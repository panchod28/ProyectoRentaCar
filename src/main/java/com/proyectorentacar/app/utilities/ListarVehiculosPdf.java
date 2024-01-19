package com.proyectorentacar.app.utilities;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.proyectorentacar.app.entity.Vehiculo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component()
public class ListarVehiculosPdf extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        // Obtiene la fecha actual
        Date fechaActual = new Date();

        // Formatea la fecha en un formato legible, por ejemplo, "dd/MM/yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = sdf.format(fechaActual);

        // Establece el nombre del archivo PDF
        String fileName = "Listado_Vehiculos.pdf"; // Cambia el nombre del archivo a lo que desees
        response.setHeader("Content-Disposition", "inline; filename=" + fileName);

        @SuppressWarnings("unchecked")
        List<Vehiculo> listadoVehiculos = (List<Vehiculo>) model.get("vehiculos");

        document.setPageSize(PageSize.LETTER.rotate());
        document.setMargins(-10, -10, 40, 10);
        document.open();

        // Define un estilo de fuente personalizado para el encabezado de la empresa
        Font fuenteEmpresa = FontFactory.getFont("Helvetica", 11, Font.BOLD, Color.BLACK);
        fuenteEmpresa.setStyle(Font.UNDERLINE); // Agrega subrayado
        fuenteEmpresa.setFamily("Arial"); // Cambia la familia de la fuente
        fuenteEmpresa.setStyle(Font.ITALIC); // Agrega cursiva

        PdfPTable tablaEncabezado = new PdfPTable(2); // Cambia el número de columnas a 2 para dividir el título del
                                                     // texto
        tablaEncabezado.setSpacingBefore(20); // Espacio antes de la tabla
        tablaEncabezado.setSpacingAfter(20); // Espacio después de la tabla
        tablaEncabezado.getDefaultCell().setBorder(Rectangle.BOX); // Agrega bordes a las celdas

        // Agrega la información de la empresa en la parte superior izquierda
        PdfPCell cellTitulo = new PdfPCell(new Phrase("Empresa:", fuenteEmpresa)); // Título
        cellTitulo.setBackgroundColor(new Color(237, 237, 237));
        cellTitulo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellTitulo.setPadding(6);
        tablaEncabezado.addCell(cellTitulo);

        PdfPCell cellValor = new PdfPCell(new Phrase("RentaCar", fuenteEmpresa)); // Valor
        cellValor.setBackgroundColor(new Color(237, 237, 237));
        cellValor.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellValor.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellValor.setPadding(6);
        tablaEncabezado.addCell(cellValor);

        cellTitulo = new PdfPCell(new Phrase("NIT:", fuenteEmpresa)); // Título
        cellTitulo.setBackgroundColor(new Color(237, 237, 237));
        cellTitulo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellTitulo.setPadding(6);
        tablaEncabezado.addCell(cellTitulo);

        cellValor = new PdfPCell(new Phrase("123456789-0", fuenteEmpresa)); // Valor
        cellValor.setBackgroundColor(new Color(237, 237, 237));
        cellValor.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellValor.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellValor.setPadding(6);
        tablaEncabezado.addCell(cellValor);

        cellTitulo = new PdfPCell(new Phrase("Dirección:", fuenteEmpresa)); // Título
        cellTitulo.setBackgroundColor(new Color(237, 237, 237));
        cellTitulo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellTitulo.setPadding(6);
        tablaEncabezado.addCell(cellTitulo);

        cellValor = new PdfPCell(new Phrase("Cra. 27 #55-68/74", fuenteEmpresa)); // Valor
        cellValor.setBackgroundColor(new Color(237, 237, 237));
        cellValor.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellValor.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellValor.setPadding(6);
        tablaEncabezado.addCell(cellValor);

        cellTitulo = new PdfPCell(new Phrase("Teléfono:", fuenteEmpresa)); // Título
        cellTitulo.setBackgroundColor(new Color(237, 237, 237));
        cellTitulo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellTitulo.setPadding(6);
        tablaEncabezado.addCell(cellTitulo);

        cellValor = new PdfPCell(new Phrase("+57 3124557639", fuenteEmpresa)); // Valor
        cellValor.setBackgroundColor(new Color(237, 237, 237));
        cellValor.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellValor.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellValor.setPadding(6);
        tablaEncabezado.addCell(cellValor);

        cellTitulo = new PdfPCell(new Phrase("Correo:", fuenteEmpresa)); // Título
        cellTitulo.setBackgroundColor(new Color(237, 237, 237));
        cellTitulo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellTitulo.setPadding(6);
        tablaEncabezado.addCell(cellTitulo);

        cellValor = new PdfPCell(new Phrase("rentacar@gmail.com", fuenteEmpresa)); // Valor
        cellValor.setBackgroundColor(new Color(237, 237, 237));
        cellValor.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellValor.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellValor.setPadding(6);
        tablaEncabezado.addCell(cellValor);

        cellTitulo = new PdfPCell(new Phrase("Fecha:", fuenteEmpresa)); // Título
        cellTitulo.setBackgroundColor(new Color(237, 237, 237));
        cellTitulo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellTitulo.setPadding(6);
        tablaEncabezado.addCell(cellTitulo);

        cellValor = new PdfPCell(new Phrase(fechaFormateada, fuenteEmpresa)); // Valor
        cellValor.setBackgroundColor(new Color(237, 237, 237));
        cellValor.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellValor.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellValor.setPadding(6);
        tablaEncabezado.addCell(cellValor);

        document.add(tablaEncabezado);

        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell celda = null;

        Font fuenteTitulo = FontFactory.getFont("Helvetica", 16, Color.WHITE);

        celda = new PdfPCell(new Phrase("LISTADO DE VEHICULOS", fuenteTitulo));
        celda.setBorder(0);
        celda.setBackgroundColor(new Color(32, 116, 154));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setPadding(30);

        tablaTitulo.addCell(celda);
        tablaTitulo.setSpacingAfter(30);

        PdfPTable tablaClientes = new PdfPTable(9);

        Font fuenteCeldas = FontFactory.getFont("Helvetica", 9); // Define una fuente más pequeña
        Font fuenteTitulos = FontFactory.getFont("Helvetica", 10, Color.WHITE); // Fuente para los títulos

        // Agrega los títulos de las columnas centrados
        PdfPCell cell = new PdfPCell(new Phrase("Identificador", fuenteTitulos));
        cell.setPadding(5);
        cell.setBackgroundColor(new Color(32, 116, 154));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tablaClientes.addCell(cell);

        cell = new PdfPCell(new Phrase("Tipo", fuenteTitulos));
        cell.setPadding(5);
        cell.setBackgroundColor(new Color(32, 116, 154));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tablaClientes.addCell(cell);

        cell = new PdfPCell(new Phrase("Modelo", fuenteTitulos));
        cell.setPadding(5);
        cell.setBackgroundColor(new Color(32, 116, 154));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tablaClientes.addCell(cell);

        cell = new PdfPCell(new Phrase("Marca", fuenteTitulos));
        cell.setPadding(5);
        cell.setBackgroundColor(new Color(32, 116, 154));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tablaClientes.addCell(cell);

        cell = new PdfPCell(new Phrase("Año", fuenteTitulos));
        cell.setPadding(5);
        cell.setBackgroundColor(new Color(32, 116, 154));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tablaClientes.addCell(cell);

        cell = new PdfPCell(new Phrase("Descripción", fuenteTitulos));
        cell.setPadding(5);
        cell.setBackgroundColor(new Color(32, 116, 154));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tablaClientes.addCell(cell);

        cell = new PdfPCell(new Phrase("Valor Día", fuenteTitulos));
        cell.setPadding(5);
        cell.setBackgroundColor(new Color(32, 116, 154));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tablaClientes.addCell(cell);

        cell = new PdfPCell(new Phrase("Estado", fuenteTitulos));
        cell.setPadding(5);
        cell.setBackgroundColor(new Color(32, 116, 154));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tablaClientes.addCell(cell);

        cell = new PdfPCell(new Phrase("Imagen", fuenteTitulos));
        cell.setPadding(5);
        cell.setBackgroundColor(new Color(32, 116, 154));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tablaClientes.addCell(cell);

        for (Vehiculo vehiculo : listadoVehiculos) {
            PdfPCell cell2 = new PdfPCell(new Phrase(vehiculo.getIdentificador(), fuenteCeldas));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2.setPadding(5); // Establece el relleno interno
            tablaClientes.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(vehiculo.getTipo(), fuenteCeldas));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2.setPadding(5); // Establece el relleno interno
            tablaClientes.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(vehiculo.getModelo(), fuenteCeldas));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2.setPadding(5); // Establece el relleno interno
            tablaClientes.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(vehiculo.getMarca(), fuenteCeldas));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2.setPadding(5); // Establece el relleno interno
            tablaClientes.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(vehiculo.getAno(), fuenteCeldas));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2.setPadding(5); // Establece el relleno interno
            tablaClientes.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(vehiculo.getDescripcion(), fuenteCeldas));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2.setPadding(5); // Establece el relleno interno
            tablaClientes.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(vehiculo.getValorDia(), fuenteCeldas));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2.setPadding(5); // Establece el relleno interno
            tablaClientes.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(vehiculo.getEstado(), fuenteCeldas));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2.setPadding(5); // Establece el relleno interno
            tablaClientes.addCell(cell2);

            cell2 = new PdfPCell();
            cell2.setPadding(5);

            // Decodifica la imagen en Base64 y la agrega al PDF
            String base64Image = vehiculo.getImagen();
            try {
                byte[] imageBytes = ImageConverter.decode(base64Image);
                Image image = Image.getInstance(imageBytes);
                image.setAlignment(Element.ALIGN_CENTER);
                image.scaleAbsolute(50, 50); // Ajusta el tamaño como desees
                cell2.addElement(image);
            } catch (IOException e) {
                // Maneja errores si la imagen no puede decodificarse
                e.printStackTrace();
            }

            tablaClientes.addCell(cell2);
        }

        document.add(tablaTitulo);
        document.add(tablaClientes);
    }
}