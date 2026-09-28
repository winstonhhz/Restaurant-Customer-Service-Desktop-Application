package com.example.restaurantcustomerservice.ChatApp;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.langchain4j.data.document.Document;
import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocuments;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatModel;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.example.restaurantcustomerservice.ChatApp.shared.Assistant;
import static com.example.restaurantcustomerservice.ChatApp.shared.Utils.glob;
import static com.example.restaurantcustomerservice.ChatApp.shared.Utils.toPath;

public class AnswerService {
    
    private Map<String, String> faqMap = new HashMap<>();
    private String faqContent = "";


    private static final Logger LOGGER = LogManager.getLogger(AnswerService.class);

    private Assistant assistant;

    private static final StreamingChatModel model = OpenAiStreamingChatModel.builder()
            .apiKey(ApiKeys.OPENAI_API_KEY)
            //.modelName(GPT_3_5_TURBO)
            .modelName(GPT_4_O_MINI)
            .build();

    public void init(SearchAction action) {
        loadFaq();
        action.appendAnswer("Initiating...");
        initChat(action);
    }

    private void initChat(SearchAction action) {
        // Document path
        List<Document> documents = loadDocuments(toPath("documents/"), glob("*.txt"));
        System.out.println("internal documents = " + documents.size());

        assistant = AiServices.builder(Assistant.class)
                .streamingChatModel(model)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                // Added Retrieval Augmented Generation (RAG) capability
                .contentRetriever(createContentRetriever(documents))   // it should have access to our documents
                .build();
        action.appendAnswer("Done");
        action.setFinished();
    }

    // createContentRetriever for the RAG
    private static ContentRetriever createContentRetriever(List<Document> documents) {
        // Here, we create an empty in-memory store for our documents and their embeddings.
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

       // Added by Steve
       /* OpenAiEmbeddingModel embeddingModel = new OpenAiEmbeddingModel.OpenAiEmbeddingModelBuilder()
                .apiKey(ApiKeys.OPENAI_API_KEY)
                //.modelName(OpenAiEmbeddingModelName.TEXT_EMBEDDING_3_SMALL)
                //.modelName(OpenAiEmbeddingModelName.TEXT_EMBEDDING_3_LARGE)
                .modelName(OpenAiEmbeddingModelName.TEXT_EMBEDDING_ADA_002)
                .build();
        EmbeddingStoreIngestor.builder()
                .embeddingModel(embeddingModel)
                .build();
        */
        // Here, we are ingesting our documents into the store.
        // Under the hood, a lot of "magic" is happening, but we can ignore it for now.
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);

        // Lastly, let's create a content retriever from an embedding store.
        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }
    void ask(SearchAction action) {
        LOGGER.info("Asking question '" + action.getQuestion() + "'");

        // 1. Build new prompt with FAQ + question
        String prompt = """
            You are a helpful assistant for a restaurant.
            Below is the FAQ:
            %s
                        
                        
            When the user asks a question, use the FAQ to answer.
            If the answer is not explicitly written but can be logically inferred, do so politely and explain your reasoning.
            
            If the question is a greeting or polite small talk, respond naturally as an assistant would.
            
            Sometimes a small typo may happen, if you can still understand it, give the answer.    
                        
            Sometimes user may type something thay doesn't related to the restaurant, you can says like how you think about that but let's focus on the restaurant related question.                                                        
                        
            If the FAQ truly does not contain any information to help answer the question, and the question is not small talk, say:
            "I'm sorry, I don't have enough information about that. Please ask something else, create a ticket or contact 012-345-6789."
                        
                       

            Question: %s
            """.formatted(getFaqContent(), action.getQuestion());

        // 2. Create response handler
        var responseHandler = new CustomStreamingResponseHandler(action);

        // 3. Send the prompt to OpenAI through your LangChain4j assistant
        assistant.chat(prompt)
                .onPartialResponse(responseHandler::onNext)
                .onCompleteResponse(responseHandler::onComplete)
                .onError(responseHandler::onError)
                .start();
    }
    
    public void loadFaq() {
    StringBuilder sb = new StringBuilder();
    String filePath = "data/faq_data.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    faqContent = sb.toString();
    System.out.println("FAQ loaded, length: " + faqContent.length());
    }

    public String getFaqContent() {
        return faqContent;
    }

    
}