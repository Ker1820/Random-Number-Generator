package sample;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/*Головний клас*/
public class Controller {

    private static final Logger logger = Logger.getLogger("sample");

    private StringBuilder warning_string = new StringBuilder();

    private Integer lower_bound_int;

    private Integer upper_bound_int;

    private Integer count_of_number_int;

    /*Представлення елементів інтерфейсу у вігляді змінних для роботи з ними*/
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField enter_lower_bound;

    @FXML
    private TextField enter_upper_bound;

    @FXML
    private TextField enter_cound_of_number;

    @FXML
    private TextArea text_area;

    @FXML
    private Button generate;

    @FXML
    private Button delete;

    /*Внутрішній клас у якому знаходяться попередження*/
    static private class Warnings {
        static final String LOWER_BOUND = "Please, enter lower bound!";
        static final String UPPER_BOUND = "Please, enter upper bound!";
        static final String COUNT_OF_NUMBER = "Please, enter count of number!";
        static final String LOWER_LESS_UPPER = "Lower bound must be less than upper bound! Please, enter correct data!";
        static final String LOWER_IS_NOT_INTEGER = "Lower bound must contains only integers! Please, enter correct data!";
        static final String UPPER_IS_NOT_INTEGER = "Upper bound must contains only integers! Please, enter correct data!";
        static final String COUNT_OF_NUMBER_IS_NOT_INTEGER = "Count of number must contains only integers! Please, enter correct data!";
        static final String MAX_VALUE = "Length of number must equals or be less than 9!";
        static final String LOWER_EQUALS_UPPER = "Lower bound can't equals to upper bound";


    }

    /*Головний метод у якому знаходиться уся логіка*/
    @FXML
    void initialize() {
//        Обробник натиснення на кнопку "Generate"
        generate.setOnAction(event -> {
            StringBuilder result = new StringBuilder();
            boolean check = check_input();
            if (check) {
//                Цикл для виводу заданої кількості рандомних чисел
                for (Integer i = 0; i < count_of_number_int; i++) {
                    result.append("" + (int) (Math.random() * (upper_bound_int - lower_bound_int + 1) + lower_bound_int) + " ");
                }
                text_area.setText(result.toString());
            } else {
                try {
//                    У разі введення не коректних даних з'являеться вікно з попередженнями
                    showErrorScene(warning_string.toString());
                } catch (IOException e) {

                }
                warning_string = new StringBuilder();
            }
        });
//      Обробник натиснення на кнопку "Delete"
        delete.setOnAction(event -> {
            text_area.setText("");
        });
    }

    //    Метод, який перевіряє коректність введених даних
//    Якщо дані не підоходять, то до рядка з попередженнями додаються ті чи інші попередження
    private boolean check_input() {
        boolean lenght_flag = true;
        boolean flag = true;
        //Перевірка нижньої межі чи містить вона щось та чи правильно введені дані

        try {
//            Перевірка на довжину числа
            if (enter_lower_bound.getText().trim().length() >= 10) {
                warning_string.append(Warnings.MAX_VALUE + "\n");
                flag = false;
                lenght_flag = false;
            }
            if (enter_lower_bound.getText().trim().isEmpty()) {
                warning_string.append(Warnings.LOWER_BOUND + "\n");
                flag = false;
            } else {
                lower_bound_int = Integer.parseInt(enter_lower_bound.getText().trim());
            }
        } catch (NumberFormatException e) {
            if (lenght_flag)
                warning_string.append(Warnings.LOWER_IS_NOT_INTEGER + "\n");
            flag = false;
        }
        lenght_flag = true;
//        Перевірка верхньої межі чи містить вона щось та чи правильно введені дані
        try {
//            Перевірка на довжину числа
            if (enter_upper_bound.getText().trim().length() >= 10) {
                warning_string.append(Warnings.MAX_VALUE + "\n");
                flag = false;
                lenght_flag = false;
            }
            if (enter_upper_bound.getText().trim().isEmpty()) {
                warning_string.append(Warnings.UPPER_BOUND + "\n");
                flag = false;
            } else {
                upper_bound_int = Integer.parseInt(enter_upper_bound.getText().trim());
            }
        } catch (NumberFormatException e) {
            if (lenght_flag)
                warning_string.append(Warnings.UPPER_IS_NOT_INTEGER + "\n");
            flag = false;
        }
        lenght_flag = true;
//        Перевірка кількості рандомних чисел межі чи містить вона щось та чи правильно введені дані
        try {
//            Перевірка на довжину числа
            if (enter_cound_of_number.getText().trim().length() >= 10) {
                warning_string.append(Warnings.MAX_VALUE + "\n");
                flag = false;
                lenght_flag = false;
            }
            if (enter_cound_of_number.getText().trim().isEmpty()) {
                warning_string.append(Warnings.COUNT_OF_NUMBER + "\n");
                flag = false;
            } else {
                count_of_number_int = Integer.parseInt(enter_cound_of_number.getText().trim());
            }
        } catch (NumberFormatException e) {
            if (lenght_flag)
                warning_string.append(Warnings.COUNT_OF_NUMBER_IS_NOT_INTEGER + "\n");
            flag = false;
        }
//        Перевірка на те, що нижня межа менша за верхю
        try {
            if (lower_bound_int != null && upper_bound_int != null && lower_bound_int > upper_bound_int) {
                logger.info("" + lower_bound_int);
                warning_string.append(Warnings.LOWER_LESS_UPPER + "\n");
                flag = false;
            }
            if (lower_bound_int != null && upper_bound_int != null && lower_bound_int.equals(upper_bound_int)) {
                warning_string.append(Warnings.LOWER_EQUALS_UPPER + "\n");
                flag = false;
            }
        } catch (NumberFormatException e) {
        }
        logger.info(warning_string.toString());

//      Якщо усе у нормі то повертається логічне значення true, в іншому випадку false
        return flag;
    }

    //    При наявності попереджень відкривається вікно з ними
    private void showErrorScene(String error_message) throws IOException {
        Stage error_stage = new Stage();
        error_stage.initModality(Modality.APPLICATION_MODAL);
        error_stage.setResizable(false);
        error_stage.setTitle("ErrorMessage");
        error_stage.setWidth(800);
        error_stage.setHeight(300);

        TextArea error_area = new TextArea(error_message);
        error_area.setFont(Font.font(20));
        error_area.setWrapText(true);
        error_area.setEditable(false);
        error_area.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

        Button close_button = new Button("Close");
        close_button.setFont(Font.font(20));
//        Обробник для закриття вікна
        close_button.setOnAction(event -> {
            error_stage.close();
        });

        VBox vBox = new VBox(error_area, close_button);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        error_stage.setScene(scene);
        error_stage.show();
    }

}

