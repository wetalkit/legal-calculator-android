package mk.wetalkit.legalcalculator;

import com.google.gson.Gson;

/**
 * Created by nikolaminoski on 9/30/17.
 */

public class Sample {

    public static <T> T getData(Class<T> dataClass) {
        return new Gson().fromJson("{\n" +
                " \"services\": [\n" +
                "  {\n" +
                "   \"title\": \"Купопродажба на имот\",\n" +
                "   \"inputs\": [\n" +
                "    {\"name\": \"Вредност на имот\", \"var\": \"vrednost_imot\", \"type\": \"Number\", \"default\": 3000000, \"comment\": \"\"},\n" +
                "    {\"name\": \"Странки во Договорот\", \"var\": \"vrednost_imot\", \"type\": \"Options\", \"default\": 0, \"comment\": \"\", \"options\": [\n" +
                "     {\"title\": \"2\", \"value\": 2},\n" +
                "     {\"title\": \"3\", \"value\": 3},\n" +
                "     {\"title\": \"4\", \"value\": 4},\n" +
                "     {\"title\": \"5\", \"value\": 5},\n" +
                "     {\"title\": \"6\", \"value\": 6},\n" +
                "     {\"title\": \"7\", \"value\": 7},\n" +
                "     {\"title\": \"8\", \"value\": 8},\n" +
                "     {\"title\": \"9\", \"value\": 9},\n" +
                "     {\"title\": \"10\", \"value\": 10}\n" +
                "    ]}\n" +
                "\n" +
                "   ]\n" +
                "  },\n" +
                "  {\n" +
                "   \"title\": \"Купопродажба на возило\",\n" +
                "   \"inputs\": [\n" +
                "    {\"name\": \"Вредност на возило\", \"var\": \"vrednost_imot\",\"type\": \"Number\", \"default\": 3000000, \"comment\": \"\"},\n" +
                "    {\"name\": \"Странки во Договорот\", \"var\": \"vrednost_imot\",\"type\": \"Options\", \"default\": 0, \"comment\": \"\", \"options\": [\n" +
                "     {\"title\": \"2\", \"value\": 2},\n" +
                "     {\"title\": \"3\", \"value\": 3},\n" +
                "     {\"title\": \"4\", \"value\": 4},\n" +
                "     {\"title\": \"5\", \"value\": 5},\n" +
                "     {\"title\": \"6\", \"value\": 6},\n" +
                "     {\"title\": \"7\", \"value\": 7},\n" +
                "     {\"title\": \"8\", \"value\": 8},\n" +
                "     {\"title\": \"9\", \"value\": 9},\n" +
                "     {\"title\": \"10\", \"value\": 10}\n" +
                "    ]}\n" +
                "\n" +
                "   ]\n" +
                "  }\n" +
                " ]\n" +
                "}", dataClass);
    }
}
