package com.vitaliif.geoguessrchallage.telegram.service;


import com.vitaliif.geoguessrchallage.geoguessr.dto.PointResult;
import com.vitaliif.geoguessrchallage.geoguessr.model.GeoGuessrChallengeResponse;
import com.vitaliif.geoguessrchallage.geoguessr.model.GeoGuessrResults;
import com.vitaliif.geoguessrchallage.telegram.model.TableRow;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageResultFormatter {

    public String formatWorstPoints(List<PointResult> worstPoints, String userName) {
        StringBuilder formattedMessage = new StringBuilder(String.format(" Старічок <b> %s </b> \nОці точки треба повторити: \n\n", userName));
        for(PointResult point: worstPoints) {
            final String link = "https://www.google.com/maps/search/?api=1&query=" + point.latitude() + "," + point.longitude();
            String message = String.format("Тут набрав %d очок", point.points());
            formattedMessage.append(String.format("<a href=\"%s\">%s</a>", link, message))
                    .append("\n");
        }

        formattedMessage.append("\nПовтори на всякий. Може попадеться ще раз колись, а може і ні.");

        return formattedMessage.toString();
    }



    //TODO: FIX in normal way
    public String formatResultsMessage(GeoGuessrResults results) {
        StringBuilder formattedMessage = new StringBuilder("<b> Today results: </b> \n\n");

        List<TableRow> tableRows = buildTableRow(results);
        int maxName = tableRows.stream().mapToInt(t -> t.name().length()).max().getAsInt();
        int maxScore = tableRows.stream().mapToInt(t -> t.totalAmount().length()).max().getAsInt();
        int maxDistance = tableRows.stream().mapToInt(t -> t.difference().length()).max().getAsInt();


        for (TableRow tableRow: tableRows) {
            formattedMessage.append("<code>").append(tableRow.name()).append(":");
            int nameLength = tableRow.name().length();
            int nameSpaces = maxName - nameLength + 2;
            formattedMessage.append(generateSpaces(nameSpaces)).append("</code>");

            int totalScoreLength = tableRow.totalAmount().length();
            int totalScoreSpaces = maxScore - totalScoreLength + 2;
            formattedMessage.append(tableRow.totalAmount()).append(generateSpaces(totalScoreSpaces));

            int differenceLength = tableRow.difference().length();
            int differenceSpaces = maxDistance - differenceLength + 2;
            formattedMessage.append(tableRow.difference()).append(generateSpaces(differenceSpaces));

            formattedMessage.append("\n");
        }


        formattedMessage.append("\n").append("Congratulation to:  ").append("<b>").append(results.items().get(0).playerName()).append("</b>");



        return formattedMessage.toString();
    }

    private List<TableRow> buildTableRow(GeoGuessrResults results) {
        return results.items().stream().map(
                i ->new TableRow(i.playerName(), i.totalScore().toString(),
                        i.game().player().totalDistance().meters().amount() + " " +
                        i.game().player().totalDistance().meters().unit())
        ).collect(Collectors.toList());
    }


    //TODO: FIX in normal way
    public String formatResultsMessage(GeoGuessrChallengeResponse geoGuessrChallengeResponse) {
        final String link = "https://www.geoguessr.com/challenge/" + geoGuessrChallengeResponse.token();
        StringBuilder formattedMessage = new StringBuilder("<code> Stop working, start playing: </code> \n\n");
        formattedMessage.append(String.format("<a href=\"%s\">%s</a>", link, "Let's go"));

        return formattedMessage.toString();
    }

    /**
     * Generates a string consisting of a specific number of spaces.
     *
     * @param length The number of spaces to generate.
     * @return A string containing the specified number of spaces.
     */
    public String generateSpaces(int length) {
        return new String(new char[length]).replace('\0', ' ');
    }


    public String formatUnknownMessage() {
        return "<b> Я хз що ти хочеш </b>";
    }
}
