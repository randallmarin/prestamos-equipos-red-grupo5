module pe.edu.uni.fiee.prestamosequiposredgrupo5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens pe.edu.uni.fiee.prestamosequiposredgrupo5 to javafx.fxml;
    opens pe.edu.uni.fiee.prestamosequiposredgrupo5.usuarios to javafx.fxml;
    exports pe.edu.uni.fiee.prestamosequiposredgrupo5;
}
