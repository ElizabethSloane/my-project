package com.project.main;

import com.project.main.model.Sight;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DBConnection {

    ParserTownLinks parserTownLinks = new ParserTownLinks();
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sights_list" +
                            "?user=root" + "&password=Yarolika75375"
            );
        }
        return connection;
    }

    public void sightRecordingToDB() throws IOException, SQLException {
        Map<String, List<Sight>> sights = parserTownLinks.getSights();
        int idOfTown = 0;
        int idOfSight = 0;

        for (Map.Entry<String, List<Sight>> entry : sights.entrySet()) {
            String town = entry.getKey();
            List<Sight> sightsOfTown = entry.getValue();

            String sqlGetTownId = "SELECT id FROM sights_list.towns WHERE name='" + town + "'";
            ResultSet resultTownId = getConnection().createStatement().executeQuery(sqlGetTownId);

            if (resultTownId.next()) {
                idOfTown = resultTownId.getInt("id");
            }
            resultTownId.close();

            for (Sight sight : sightsOfTown) {
                ++idOfSight;
                String sqlPutSight = "INSERT INTO sights_list.sights(`id`, `id_town`, `information`, `name`) VALUES('" +
                        idOfSight + "', '"+ idOfTown + "', '" + null + "', '" + sight.getName() + "')";
                getConnection().createStatement()
                        .execute(sqlPutSight);
            }
        }

    }
}
