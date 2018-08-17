package web;

public class Turma {
    private String discCod;
    private String discNome;
    private String discCarga;
    private String turmaCod;
    private String turmaHoras;
    public Turma(){
        setDiscCod(null);
        setDiscNome(null);
        setDiscCarga(null);
        setTurmaCod(null);
        setTurmaHoras(null);
    }
    
    public String getDiscCod() {
        return discCod;
    }

    public void setDiscCod(String discCod) {
        this.discCod = discCod;
    }

    public String getDiscNome() {
        return discNome;
    }

    public void setDiscNome(String discNome) {
        this.discNome = discNome;
    }

    public String getDiscCarga() {
        return discCarga;
    }

    public void setDiscCarga(String discCarga) {
        this.discCarga = discCarga;
    }

    public String getTurmaCod() {
        return turmaCod;
    }

    public void setTurmaCod(String turmaCod) {
        this.turmaCod = turmaCod;
    }

    public String getTurmaHoras() {
        return turmaHoras;
    }

    public void setTurmaHoras(String turmaHoras) {
        this.turmaHoras = turmaHoras;
    }
    
}
