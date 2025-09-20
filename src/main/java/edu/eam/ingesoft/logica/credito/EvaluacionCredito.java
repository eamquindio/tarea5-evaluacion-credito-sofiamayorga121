package edu.eam.ingesoft.logica.credito;

/**
 * Clase que representa una evaluación de crédito para la entidad financiera FinAurora.
 * Permite calcular cuotas mensuales y evaluar la aprobación de créditos según reglas de negocio.
 */
public class EvaluacionCredito {

    private String nombreSolicitante;
    private double ingresosMensuales;
    private int numeroCreditosActivos;
    private int puntajeCredito;
    private double valorCreditoSolicitado;
    private boolean tieneCodedor;

    /**
     * Constructor de la clase EvaluacionCredito.
     *
     * @param nombreSolicitante Nombre completo del solicitante del crédito
     * @param ingresosMensuales Ingresos mensuales del solicitante en pesos
     * @param numeroCreditosActivos Cantidad de créditos activos que tiene el solicitante
     * @param puntajeCredito Puntaje crediticio del solicitante (0-1000)
     * @param valorCreditoSolicitado Monto del crédito solicitado en pesos
     * @param tieneCodedor Indica si el solicitante cuenta con un codeudor
     */
    public EvaluacionCredito(String nombreSolicitante, double ingresosMensuales,
                             int numeroCreditosActivos, int puntajeCredito,
                             double valorCreditoSolicitado, boolean tieneCodedor) {
        this.nombreSolicitante = nombreSolicitante;
        this.ingresosMensuales = ingresosMensuales;
        this.numeroCreditosActivos = numeroCreditosActivos;
        this.puntajeCredito = puntajeCredito;
        this.valorCreditoSolicitado = valorCreditoSolicitado;
        this.tieneCodedor = tieneCodedor;
    }

    /**
     * Calcula la tasa de interés mensual a partir de la tasa nominal anual.
     *
     * @param tasaNominalAnual Tasa nominal anual en porcentaje
     * @return Tasa mensual como valor decimal (por ejemplo, 0.015 para 1.5%)
     */
    public double calcularTasaMensual(double tasaNominalAnual) {
        return tasaNominalAnual / 12.0;
    }

    /**
     * Calcula la cuota mensual del crédito usando la fórmula de amortización francesa.
     * Fórmula: Cuota = M * (im * (1+im)^n) / ((1+im)^n - 1)
     *
     * @param tasaNominalAnual Tasa nominal anual en porcentaje
     * @param plazoMeses Plazo del crédito en meses
     * @return Valor de la cuota mensual en pesos
     */
    public double calcularCuotaMensual(double tasaNominalAnual, int plazoMeses) {
        double im = calcularTasaMensual(tasaNominalAnual); // Tasa mensual en decimal
        double M = valorCreditoSolicitado;

        if (im == 0) {
            return M / plazoMeses; // Crédito sin intereses
        }

        double factor = Math.pow(1 + im, plazoMeses);
        double cuota = M * (im * factor) / (factor - 1);
        return cuota;
    }

    /**
     * Evalúa si el crédito debe ser aprobado según las reglas de negocio:
     * - Perfil bajo (puntaje < 500): Rechazo automático
     * - Perfil medio (500 ≤ puntaje ≤ 700): Requiere codeudor y cuota ≤ 25% de ingresos
     * - Perfil alto (puntaje > 700 y < 2 créditos): Cuota ≤ 30% de ingresos
     *
     * @param tasaNominalAnual Tasa nominal anual en porcentaje
     * @param plazoMeses Plazo del crédito en meses
     * @return true si el crédito es aprobado, false si es rechazado
     */
    public boolean evaluarAprobacion(double tasaNominalAnual, int plazoMeses) {
        double cuotaMensual = calcularCuotaMensual(tasaNominalAnual, plazoMeses);

        if (puntajeCredito < 500) {
            return false; // Rechazo automático
        } else if (puntajeCredito <= 700) {
            // Perfil medio
            return tieneCodedor && cuotaMensual <= ingresosMensuales * 0.25;
        } else {
            // Perfil alto
            return numeroCreditosActivos < 2 && cuotaMensual <= ingresosMensuales * 0.30;
        }
    }

    // Getters y Setters

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public double getIngresosMensuales() {
        return ingresosMensuales;
    }

    public void setIngresosMensuales(double ingresosMensuales) {
        this.ingresosMensuales = ingresosMensuales;
    }

    public int getNumeroCreditosActivos() {
        return numeroCreditosActivos;
    }

    public void setNumeroCreditosActivos(int numeroCreditosActivos) {
        this.numeroCreditosActivos = numeroCreditosActivos;
    }

    public int getPuntajeCredito() {
        return puntajeCredito;
    }

    public void setPuntajeCredito(int puntajeCredito) {
        this.puntajeCredito = puntajeCredito;
    }

    public double getValorCreditoSolicitado() {
        return valorCreditoSolicitado;
    }

    public void setValorCreditoSolicitado(double valorCreditoSolicitado) {
        this.valorCreditoSolicitado = valorCreditoSolicitado;
    }

    public boolean isTieneCodedor() {
        return tieneCodedor;
    }

    public void setTieneCodedor(boolean tieneCodedor) {
        this.tieneCodedor = tieneCodedor;
    }
}

