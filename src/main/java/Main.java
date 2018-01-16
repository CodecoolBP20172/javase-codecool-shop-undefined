import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;


import com.codecool.shop.connection.ConnectionManager;
import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.*;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    public static void main(String[] args) {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        // populate some data for the memory storage
        // populateData();

        //logging test
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("Logging setup is working");

        // Always add generic routes to the end
        get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());
        // Equivalent with above
        get("/index", (Request req, Response res) ->
                new ThymeleafTemplateEngine().render(ProductController.renderProducts(req, res)));

        post("/payment", (Request req, Response res) ->
                new ThymeleafTemplateEngine().render(ProductController.renderPayment(req, res)));

        post("/checkout", (Request req, Response res) ->
                new ThymeleafTemplateEngine().render(ProductController.renderCheckout(req, res)));

        post("/confirmation", (Request req, Response res) ->
                new ThymeleafTemplateEngine().render(ProductController.renderConfirmation(req, res)));

        post("/register", (Request req, Response res) ->
                new ThymeleafTemplateEngine().render(ProductController.renderRegister(req, res)));

        get("/registration", (Request req, Response res) ->
                new ThymeleafTemplateEngine().render(ProductController.renderRegistrationPage(req, res)));


        get("*", (Request req, Response res) ->
                new ThymeleafTemplateEngine().render(ProductController.renderError(req, res)));

        // Add this line to your project to enable the debug screen
        enableDebugScreen();
    }

    private static void populateData() {

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier (memory)
        /*Supplier magicWandFactory = new Supplier("Magic Wand Factory", "Quality wands for good and evil wizards");
        supplierDataStore.add(magicWandFactory);
        Supplier magicSweets = new Supplier("Magic Sweets", "Tricky sweets for wizards");
        supplierDataStore.add(magicSweets);
        Supplier wizardTools = new Supplier("Wizard Tools", "Everyday tools for wizards");
        supplierDataStore.add(wizardTools);

        //setting up a new product category (memory)
        ProductCategory hogwarts = new ProductCategory("Hogwarts", "Magical items", "Items for wizards");
        productCategoryDataStore.add(hogwarts);

        //setting up products and printing it (memory)
        productDataStore.add(new Product("Magic Wand - model 1", 300, "USD", "High quality 39.8cm long wand for great wizards", hogwarts, magicWandFactory));
        productDataStore.add(new Product("Magic Wand - model 2", 350, "USD", "High quality 36cm long wand for brave wizards", hogwarts, magicWandFactory));
        productDataStore.add(new Product("Magic Wand - model 3", 390, "USD", "High quality 39.8cm long wand for evil wizards", hogwarts, magicWandFactory));
        productDataStore.add(new Product("Time-Turner Necklace", 44, "USD", "The Time-Turner is centred with a working miniature hourglass.", hogwarts, wizardTools));
        productDataStore.add(new Product("Marauder's Map", 30, "USD", "With this item you'll always know where your friends are.", hogwarts, wizardTools));
        productDataStore.add(new Product("Wizard Chess Set", 100, "USD", "Simple chess game with an epic twist.", hogwarts, wizardTools));
        productDataStore.add(new Product("Exploding Bon Bons", 8, "USD", "White chocolate with an Orange & Pineapple flavour truffle centre.", hogwarts, magicSweets));
        productDataStore.add(new Product("Every Flavour Beans", 9, "USD", "Up to 20 flavours that range from delicious to disgusting.", hogwarts, magicSweets));
        productDataStore.add(new Product("Chocolate Frog", 8, "USD", "A delicious frog shaped confection of solid milk chocolate.", hogwarts, magicSweets));

    }
    */


    }
}
