package admin;

import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.awt.*;

public class Prueba extends HBox {

    Circle photoBase;
    Text name;
    Text email;
    Text followers;
    Text following;
    boolean isChef;



    public Prueba(String name, String email, int followers, int following, boolean isChef){

        this.photoBase = new Circle();
        photoBase.setRadius(3);

        this.name = new Text(name);

        this.email = new Text(email);


        this.followers = new Text(String.valueOf(followers));
        this.following = new Text(String.valueOf(following));

        this.isChef = isChef;

        this.buildHBox();

    }

    private void buildHBox(){

        this.setSpacing(10);

        this.getChildren().addAll(photoBase, name, email, followers, following);
    }


}
