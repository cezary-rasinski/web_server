package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
public class RectangleController {
    /*ZAD2.2 Napisz kontroler REST RectangleController posiadający metodę,
    której wywołanie spowoduje wyświetlenie obiektu Rectangle zmapowanego na JSON.*/
    @GetMapping("rectangle")
    public Rectangle getRectangle (){
        Rectangle rectangle = new Rectangle(
                10,
                20,
                30,
                40,
                "gray");
        return rectangle;
    }

    //ZAD3. W kontrolerze:
    //Stwórz prywatną listę prostokątów.
    private List<Rectangle> rectangles = new ArrayList();
    //Napisz metodę, która dodaje prostokąt o określonych w kodzie parametrach.
    @PostMapping("addRectangle")
    public int addRectangle(){
        Rectangle rectToAdd= new Rectangle(120,70,100,30,"green");
        rectangles.add(rectToAdd);

        return rectangles.size();
    }
    //Napisz metodę, która wygeneruje napis zawierający kod SVG z prostokątami znajdującymi się na liście.
    @GetMapping("svg")
    public String toSvg(){
        StringBuilder sb=new StringBuilder();
        sb.append("<svg>\n");
        sb.append("<svg height=\"800\" width=\"3000\" xmlns=\"http://www.w3.org/2000/svg\">\n" );

        for (Rectangle rect : rectangles){
            sb.append(String.format("\"<rect width=\"%d\" height=\"%d\" x=\"%d\" y=\"%d\"\n fill=\"%s\" />\n",rect.getWidth(),rect.getHeight(),rect.getX(),rect.getY(),rect.getColor()));
        }
        sb.append("<svg>");
        return sb.toString();
    }
    //Napisz metodę, która zwróci listę prostokątów zmapowaną na JSON.
    @GetMapping("getRectangles")
    public List<Rectangle> getRectangles(){
        return rectangles;
    }
    /*Zadanie 4.
    Zmodyfikuj metodę dodającą prostokąt, by odpowiadała na żądanie HTTP POST.
    Niech metoda przyjmuje prostokąt, który zostanie zdefiniowany w ciele żądania HTTP.*/
    @PostMapping("addCustomRectangle")
    public int addRectangle(@RequestBody Rectangle rectangle) {
        //dekorator @requestBody pozwala 'uzupelnic' nasz rectangle
        rectangles.add(rectangle);
        // how to add with curl new ver
        /*curl.exe -X POST -H "Content-Type: application/json"
        -d '[{"x":10,"y":20,"height":32,"width":30,"color":"gray"}]'
        localhost:8090/addRectangle (all in one line)*/
        return rectangles.size();
        //we can use POST with postman
        //how to do from linux terminal: curl -X POST our_link(localhost:8090/addRectangle)
        // from windows: curl.exe -X POST localhost:8090/addRectangle
    }
    /*Zadanie 5.
    Napisz metody:
    GET z argumentem typu int,  zwracającą prostokąt w liście o podanym indeksie.*/
    @GetMapping("getIdRectangle/{id}") // /{smh} we can put a specific value at that id
    public Rectangle getIndexRectangle(@PathVariable Long id){
        return rectangles.get(id.intValue());
    }
    // in browser after adding elemnets: http://localhost:8090/rectangle/3
    /*PUT z argumentem typu int i argumentem typu Rectangle, modyfikującą istniejący na liście
     pod tym indeksem prostokąt na prostokąt przekazany argumentem.*/
    @PutMapping("modifyIdRectangle/{id}")
    public Rectangle modifyIndexRectangle(@PathVariable Long id, @RequestBody Rectangle rectangle){
        if (id >= 0 && id < rectangles.size()) {
            rectangles.set(id.intValue(), rectangle);
            return rectangles.get(id.intValue());
        }
        else
            return null;
    }
    /*DELETE  z argumentem typu int, usuwającą prostokąt z listy z miejsca o podanym indeksie.*/
    @DeleteMapping("deleteIdRectangle/{id}")
    public String removeIndexRectangle(@PathVariable Long id){
        if (id >= 0 && id < rectangles.size()) {
            rectangles.remove(id.intValue());
            return "succes";
        }
        else
            return "fail";
    }
}
