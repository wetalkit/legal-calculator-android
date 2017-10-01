package mk.wetalkit.legalcalculator;

import com.google.gson.Gson;

import mk.wetalkit.legalcalculator.data.TotalCost;

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
                "    {\"name\": \"Број на асдасд\", \"var\": \"vrednost_imot\", \"type\": \"Number\", \"mandatory\": true, \"default\": 123, \"comment\": \"\"},\n" +
                "    {\"name\": \"Награда за упис во кат.\", \"var\": \"vrednost_imot\", \"type\": \"Number\", \"mandatory\": false, \"default\": 45, \"comment\": \"\"},\n" +
                "    {\"name\": \"Број на асдасд\", \"var\": \"vrednost_imot\", \"type\": \"Number\", \"mandatory\": false, \"default\": 345, \"comment\": \"\"},\n" +
                "    {\"name\": \"Број на асдасд\", \"var\": \"vrednost_imot\", \"type\": \"Number\", \"mandatory\": false, \"default\": 34534, \"comment\": \"\"},\n" +
                "    {\"name\": \"Број на асдасд\", \"var\": \"vrednost_imot\", \"type\": \"Number\", \"mandatory\": false, \"default\": 657, \"comment\": \"\"},\n" +
                "    {\"name\": \"Број на асдасд\", \"var\": \"vrednost_imot\", \"type\": \"Number\", \"mandatory\": false, \"default\": 565, \"comment\": \"\"},\n" +
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

    public static TotalCost getResults(Class<TotalCost> totalCostClass) {
        return new Gson().fromJson("{\n" +
                "\t\"services_costs\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"title\": \"lawyer\", \n" +
                "\t\t\t\"description\": \"Адвокат\",\n" +
                "\t\t\t\"costs\": [\n" +
                "\t\t\t\t{\"title\": \"Договор\", \"cost\": 3400}\n" +
                "\t\t\t]\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"title\": \"notary\", \n" +
                "\t\t\t\"description\": \"Нотар\",\n" +
                "\t\t\t\"costs\": [\n" +
                "\t\t\t\t{ \"title\": \"Солемнизација\", \"cost\": 3300 },\n" +
                "\t\t\t\t{ \"title\": \"Заверка\", \"cost\": 5300 }\n" +
                "\t\t\t\t{ \"title\": \"Награда за упис во кат.\", \"cost\": 5300 }\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"total-min\": 12000,\n" +
                "\t\"total-max\": 12000\n" +
                "}", TotalCost.class);
    }
}
