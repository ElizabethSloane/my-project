package com.project.main;

import com.project.main.model.Sight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ParserTownLinks {

    String url = "https://ru.wikipedia.org/wiki/%D0%93%D0%BE%D1%80%D0%BE%D0%B4%D1%81%D0%BA%D0%B8%D0%B5_%D0%BD%D0%B0%D1%81%D0%B5%D0%BB%D1%91%D0%BD%D0%BD%D1%8B%D0%B5_%D0%BF%D1%83%D0%BD%D0%BA%D1%82%D1%8B_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B9_%D0%BE%D0%B1%D0%BB%D0%B0%D1%81%D1%82%D0%B8";

    Map<String, List<Sight>> sights = new TreeMap<>();
    ArrayList<Link> listOfTowns = new ArrayList<>();

    public ArrayList<Link> getListOfTowns() throws IOException {
        Document siteParse = Jsoup.connect(url).get();
        ArrayList<Link> links = new ArrayList<>();
        siteParse.select("tbody").select("td").select("a").
                forEach(
                        element -> {
                            String nameOfTown = element.text();
                            String linkOfTown = element.attr("href");
                            if (nameOfTown.matches("[^0-9]+")) {
                                links.add(new Link(nameOfTown, linkOfTown));
                            }
                        }
                );
        //удаление из списка регионов городов
        int i=1;
        for (Link link1 : links) {
            if (i == 1) {
                listOfTowns.add(new Link(link1.getNameOfTown(),"https://ru.wikipedia.org" + link1.getLink()));
                i++;
            }
            else {
                i--;
            }
            if (link1.getNameOfTown().equals("Яхрома")) {
                break;
            }
        }
        return listOfTowns;
    }


    public Map<String, List<Sight>> getSights() throws IOException {
        ArrayList<Link> listOfTowns = getListOfTowns();
        Document linkParse;

        for (Link link1 : listOfTowns) {
            String town = link1.getNameOfTown();
            List<Sight> sightsOfTown = new ArrayList<>();
            linkParse = Jsoup.connect(link1.getLink()).get();
            linkParse.select("span").
                    forEach(
                            element -> {
                                String nameOfSight = element.attr("id");
                                if (isSight(nameOfSight)) {
                                    String correctNameOfSight = nameOfSight.replaceAll("_", " ");
                                    Sight sight = new Sight();
                                    sight.setName(correctNameOfSight);
                                    sightsOfTown.add(sight);
                                    sights.put(town, sightsOfTown);
                                }
                            }
                    );
        }
        return sights;
    }

    public boolean isSight(String sight) {
        if (sight.matches("[^0-9]+") &&
        sight.contains("садьба") ||
                sight.contains("парк") ||
                sight.contains("храм") ||
                sight.contains("завод") ||
                sight.contains("ворец") ||
                sight.contains("кремл") ||
                sight.contains("онастыр") ||
                sight.contains("ерковь") ||
                sight.contains("узей"))  {
            return true;
        }
        return false;
    }
}
