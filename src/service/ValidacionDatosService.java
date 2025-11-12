package service;

public class ValidacionDatosService {

    public ValidacionDatosService(){
    }

    public static boolean validarNombre(String str){
            return str.matches("^[a-zA-Z]+$");
    }

    public static boolean validarEmail(String str){
        return str.contains("@");
    }

    public static boolean validarNumIdentificacion(String str){
        return str.matches("^[0-9]+$");
    }

    public static boolean validarNumTelefono(String str){
        return str.matches("^[0-9]+$");
    }

}
