package es.udc.fic.ipm.beers;

import android.text.TextUtils;

import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wifopc on 2/11/17.
 */

public class BeerModel {
    private static List<Beer> beers;
    /**
     * Fetch a list of names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     * @return List of names and majors
     * @throws IOException
     */
    public static List<Beer> getDataFromApi(com.google.api.services.sheets.v4.Sheets mService) throws IOException {
        //String spreadsheetId = "119D2l8Tc8GviGR4u9SxWmj4Fj8lB0d63Nm_QyNEXHsE";
        String spreadsheetId = "1vPGNG_ek5T5I-1KQVPAhwMv6YOJEY1Dg5ZIhCPHA23I";
        String range = "Sheet1!A2:G";
        List<String> results = new ArrayList<String>();
        ValueRange response = mService.spreadsheets().values()
                .get(spreadsheetId, range)
                .setValueRenderOption("FORMULA").execute();

        List<List<Object>> values = response.getValues();
        List<Beer> tempbeers = new ArrayList<>();
        if (values != null) {
            results.add("Nombre, Fecha, Origen, Clase, Comentarios, Más info");

            for (List row : values) {
                //cada fila tiene un numero de columnas igual a las columnas que tengan texto,
                //por ejemplo, la fila de budweiser tendra una sola columna!
                String name = null, date = null, madeIn = null,
                        type = null, comment = null, moreInfo = null,
                        photoURL = null;
                int tam = row.size();
                //cogemos todos los campos de la tabla, separandolos con ", "
                switch (tam) {
                    case 1: {
                        results.add((String)row.get(0));
                        //si la celda no está vacía, actualizo el campo
                        if (!TextUtils.isEmpty((String)row.get(0)))
                            name = (String)row.get(0);
                        break;
                    }
                    case 2: {
                        results.add(row.get(0) + ", " +  row.get(1));
                        if (!TextUtils.isEmpty((String)row.get(0)))
                            name = (String)row.get(0);
                        if (!TextUtils.isEmpty((String)row.get(1)))
                            date = (String)row.get(1);
                        break;
                    }
                    case 3: {
                        results.add(row.get(0) + ", " + row.get(1) + ", " + row.get(2));
                        if (!TextUtils.isEmpty((String)row.get(0)))
                            name = (String)row.get(0);
                        if (!TextUtils.isEmpty((String)row.get(1)))
                            date = (String)row.get(1);
                        if (!TextUtils.isEmpty((String)row.get(2)))
                            madeIn = (String)row.get(2);
                        break;
                    }
                    case 4: {
                        results.add(row.get(0) + ", " + row.get(1) + ", " + row.get(2) + ", "
                                + row.get(3));
                        if (!TextUtils.isEmpty((String)row.get(0)))
                            name = (String)row.get(0);
                        if (!TextUtils.isEmpty((String)row.get(1)))
                            date = (String)row.get(1);
                        if (!TextUtils.isEmpty((String)row.get(2)))
                            madeIn = (String)row.get(2);
                        if (!TextUtils.isEmpty((String)row.get(3)))
                            type = (String)row.get(3);
                        break;
                    }
                    case 5: {
                        results.add(row.get(0) + ", " + row.get(1) + ", " + row.get(2) + ", "
                                + row.get(3) + ", " + row.get(4));
                        if (!TextUtils.isEmpty((String)row.get(0)))
                            name = (String)row.get(0);
                        if (!TextUtils.isEmpty((String)row.get(1)))
                            date = (String)row.get(1);
                        if (!TextUtils.isEmpty((String)row.get(2)))
                            madeIn = (String)row.get(2);
                        if (!TextUtils.isEmpty((String)row.get(3)))
                            type = (String)row.get(3);
                        if (!TextUtils.isEmpty((String)row.get(4)))
                            comment = (String)row.get(4);
                        break;
                    }
                    case 6: {
                        results.add(row.get(0) + ", " + row.get(1) + ", " + row.get(2) + ", "
                                + row.get(3) + ", " + row.get(4) + ", " + row.get(5));
                        if (!TextUtils.isEmpty((String)row.get(0)))
                            name = (String)row.get(0);
                        if (!TextUtils.isEmpty((String)row.get(1)))
                            date = (String)row.get(1);
                        if (!TextUtils.isEmpty((String)row.get(2)))
                            madeIn = (String)row.get(2);
                        if (!TextUtils.isEmpty((String)row.get(3)))
                            type = (String)row.get(3);
                        if (!TextUtils.isEmpty((String)row.get(4)))
                            comment = (String)row.get(4);
                        if (!TextUtils.isEmpty((String)row.get(5)))
                            moreInfo = (String)row.get(5);
                        break;
                    }
                    case 7: {
                        results.add(row.get(0) + ", " + row.get(1) + ", " + row.get(2) + ", "
                                + row.get(3) + ", " + row.get(4) + ", " + row.get(5) + ", "
                                + row.get(6));
                        if (!TextUtils.isEmpty((String)row.get(0)))
                            name = (String)row.get(0);
                        if (!TextUtils.isEmpty((String)row.get(1)))
                            date = (String)row.get(1);
                        if (!TextUtils.isEmpty((String)row.get(2)))
                            madeIn = (String)row.get(2);
                        if (!TextUtils.isEmpty((String)row.get(3)))
                            type = (String)row.get(3);
                        if (!TextUtils.isEmpty((String)row.get(4)))
                            comment = (String)row.get(4);
                        if (!TextUtils.isEmpty((String)row.get(5)))
                            moreInfo = (String)row.get(5);
                        if (!TextUtils.isEmpty((String)row.get(6))) {
                            photoURL = (String)row.get(6);
                            //cogemos el texto que hay entre las comillas
                            Pattern p = Pattern.compile("\"([^\"]*)\"");
                            Matcher m = p.matcher(photoURL);
                            while (m.find()) {
                                photoURL = m.group(1);
                            }
                            //otra forma de hacerlo
                            /*cogemos lo que hay entre parentesis y quitamos las comillas
                            String result = photoURL.substring(photoURL.indexOf("(") + 1, photoURL.indexOf(")"));
                            result.substring(1, result.length()-1);*/
                        }
                        break;
                    }
                    default:
                        break;
                }
                //creamos la cerveza y la metemos en la lista de cervezas
                Beer beer = new Beer(name, date, madeIn, type,
                        comment, moreInfo, photoURL);
                tempbeers.add(beer);
            }
        }
        //printList(tempbeers);
        beers = tempbeers;
        return tempbeers;
    }

    //just for debug
    private static void printList(List<Beer> birras) {
        for (Beer beer : birras) {
            System.out.println("nombre: " + beer.getName() + "\t fecha: " + beer.getDate()
                    + "\t origen: " + beer.getMadeIn() + "\t clase: " + beer.getType()
                    + "\t comentarios: " + beer.getComment() + "\t mas info: " + beer.getMoreInfo()
                    + "\t url de foro: " + beer.getPhotoURL());
        }
    }

    public static List<Beer> getBeers (){
        return beers;
    }

    public static int findByName(String name) {
        for (int i = 0; i < beers.size(); i++) {
            //System.out.println("nombre_stock: " + beers.get(i).getName() + " nombre parametro: " + name);
            //System.out.println("igual?? " + (beers.get(i).getName().equals(name)));
            if (beers.get(i).getName().equals(name)) {
                return i;
            }

        }
        return (-1);
    }
}
