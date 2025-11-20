package model;

public class Consultorio {
    int id;
    String especialidad;
    int piso;

    public Consultorio(int id, String especialidad, int piso) {
        this.id = id;
        this.especialidad = especialidad;
        this.piso = piso;
    }

    public int getId() {
        return id;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public String toCSV(){
        return getId() + ";" + getEspecialidad() + ";" + getPiso();
    }

    public String toString(){
        return id + " " + especialidad + " " + piso;
    }

}
