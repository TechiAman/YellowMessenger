package com.yellow.messenger.bot.util;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.cloud.dialogflow.v2.TextInput.Builder;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * DialogFlow API Detect Intent sample with text inputs.
 */


public class DetectIntentTexts {
    // [START dialogflow_detect_intent_text]

    /**
     * Returns the result of detect intent with texts as inputs.
     *
     * Using the same `session_id` between requests allows continuation of the conversation.
     *
     * @param projectId    Project/Agent Id.
     * @param texts        The text intents to be detected based on what a user says.
     * @param sessionId    Identifier of the DetectIntent session.
     * @param languageCode Language code of the query.
     * @return The QueryResult for each input text.
     */
    public static Map<String, QueryResult> detectIntentTexts(
            String projectId,
            List<String> texts,
            String sessionId,
            String languageCode) throws Exception {
        Map<String, QueryResult> queryResults = Maps.newHashMap();
        // Instantiates a client
        try (SessionsClient sessionsClient = SessionsClient.create()) {
            // Set the session name using the sessionId (UUID) and projectID (my-project-id)
            SessionName session = SessionName.of(projectId, sessionId);

            // Detect intents for each text input
            for (String text : texts) {
                // Set the text (hello) and language code (en-US) for the query
                Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode(languageCode);

                // Build the query with the TextInput
                QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

                // Performs the detect intent request
                DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

                // Display the query result
                QueryResult queryResult = response.getQueryResult();

                System.out.format("Yellow Messenger bot: '%s'\n", queryResult.getFulfillmentText());

                queryResults.put(text, queryResult);
            }
        }
        return queryResults;
    }
    // [END dialogflow_detect_intent_text]
}