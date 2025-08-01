var token = $('#csrfToken').val();

$(document).on("change", "#condicionImpuesto", function () {

  sumarIva();
  restarAIva();

  var v = $(this).val();

  setTimeout(function(){
    if(v == '01'){
      //No hago nada, dejo los nodos en readonly
      if( parseFloat($("#restarAIvaTotal").val()) > 0 ){
        $("#montoTotalImpuestoAcreditar").val( $("#ivaTotal").val() );
      }else{
        $("#montoTotalImpuestoAcreditar").val( $("#impuestos").val() );
      }
    }

    if(v == '04'){
      $("#montoTotalImpuestoAcreditar").val( "" );
      $("#montoTotalDeGastoAplicable").val( "" );
    }

    if(v == '05'){
      $("#montoTotalImpuestoAcreditar").val( "" );
      $("#montoTotalDeGastoAplicable").val( "" );
    }

    if(v == '02'){
      //Habilito para escribir
      $("#montoTotalImpuestoAcreditar").val( "" );
      $("#montoTotalDeGastoAplicable").val( "" );

      $("#montoTotalImpuestoAcreditar").prop("readonly", false);
      $("#montoTotalDeGastoAplicable").prop("readonly", false);
    }else{
      //Los dejo
      $("#montoTotalImpuestoAcreditar").prop("readonly", true);
      $("#montoTotalDeGastoAplicable").prop("readonly", true);
    }

    if(v == '03'){
      //Asigno el valor del total de impuestos a montoTotalImpuestoAcreditar
      if( parseFloat($("#restarAIvaTotal").val()) > 0 ){
        $("#montoTotalImpuestoAcreditar").val( $("#ivaTotal").val() );
      }else{
        $("#montoTotalImpuestoAcreditar").val( $("#impuestos").val() );
      }
    }

  }, 200);

});

$("#formAddMensajeReceptor").submit(function( event ) {

  var m1 = $("#montoTotalImpuestoAcreditar").val();
  var iva = $("#impuestos").val();

  if(parseFloat(m1) > parseFloat(iva)){

    swal("","El Monto del impuesto a acreditar NO puede ser mayor que los impuesto de la factura","warning");
    return false;

  }

  var m2 = $("#montoTotalDeGastoAplicable").val();
  var t  = $("#totalFactura").val();

  var m3 = parseFloat(m1) + parseFloat(m2);

  if(m3 > t){
    swal("","La suma del Monto del impuesto a acreditar + Monto total de gasto a aplicar no puede ser mayor que el total de la factura.","warning");
    return false;
  }

  $.ajax({
    type: "POST",
    cache: false,
    beforeSend: function () {
      $("#btn-procesar-mr").prop('disabled', true);
      $("#btn-procesar-mr").text('[(#{txt.btn.procesando})]');
      loadingShow();
    },
    url: "[(@{/mensaje-receptor/recepcion})]",
    data: $(this).serialize(),
    success: function (data)
    {

      if(parseInt(data.response) === 200){

        $("#formAddMensajeReceptor").trigger("reset");

        $('#tipoDocumento, #condicionImpuesto').select2();

        swal("", "[(#{txt.mr.alerta.exito1})] "+data.clave+", [(#{txt.mr.alerta.exito2})] "+data.consecutivo+".", "success");

        $("#lineasFactura").html("");

        setTimeout(function(){
          location.href="[(@{/mensaje-receptor-inbox/})]"
        }, 2000);

      }else if(parseInt(data.response) === 401){
        swal("", data.msj, "warning");
      }else if(parseInt(data.response) === 0){
        location.href="[(@{/})]"
      }else{
        swal("[(#{txt.mr.alerta})]","[(#{txt.mr.alerta.msj})]","warning");
      }

    }, complete: function () {
      $("#btn-procesar-mr").prop('disabled', false);
      $("#btn-procesar-mr").text('[(#{txt.aceptar.factura})]');
      loadingHide();
    }, error: function (x, t, m) {
      if (t === "timeout") {

      } else {

      }
    }
  });

  return false;
});


function cleanForm(){
  $("#codigoActividad").val("0.00");
  $("#clave").val("0.00");
  $("#fechaEmision").val("0.00");
  $("#moneda").val("0.00");
  $("#condicionVenta").val("0.00");
  $("#plazoCredito").val("0.00");
  $("#medioPago").val("0.00");

  $("#tipoCambio").val("0.00");
  $("#emisorMr").val("0.00");
  $("#identificacionEmisorMr").val("0.00");
  $("#nombreComercialEmisorMr").val("0.00");
  $("#correoEmisorMr").val("0.00");
  $("#tipoIdentificacionEmisorMr").val("0.00");
  $("#nombreReceptorMr").val("0.00");
  $("#nombreComercialReceptorMr").val("0.00");
  $("#tipoIdentificacionReceptorMr").val("0.00");
  $("#identificacionReceptorMr").val("0.00");
  $("#totalServGravados").val("0.00");
  $("#totalServExentos").val("0.00");
  $("#totalServExonerado").val("0.00");
  $("#totalMercGravadas").val("0.00");
  $("#totalMercExentas").val("0.00");
  $("#totalMercExonerada").val("0.00");
  $("#totalGravados").val("0.00");
  $("#totalExentos").val("0.00");
  $("#totalExonerado").val("0.00");
  $("#totalVentas").val("0.00");
  $("#totalDescuentos").val("0.00");
  $("#totalVentaNeta").val("0.00");
  $("#impuestos").val("");
  $("#totalIVADevuelto").val("0.00");
  $("#totalOtrosCargos").val("0.00");
  $("#totalFactura").val("0.00");
  $("#lineasFactura").html("0.00"); //Limpio la tabla
}

leerXml();

function leerXml() {

  cleanForm();

  var text = $("#loadFacturaXml").val();
  var node = document.getElementById("#loadFacturaXml");
  //node.innerText = text;
  var $xml;
  var xml = text,
      xmlDoc = $.parseXML(xml),
      $xml = $(xmlDoc);
  var codigoActividad = $xml.find("CodigoActividad");
  var Clave = $xml.find("Clave");

  $xml.find("TotalServGravados").text() === "" ? "0.00" : $xml.find("TotalServGravados").text();

  var FechaEmision = $xml.find("FechaEmision").first();
  var CodigoMoneda = $xml.find("CodigoMoneda").text() === "" ? "CRC" : $xml.find("CodigoMoneda").text();
  var TipoCambio = $xml.find("TipoCambio").text() === "" ? "1.00" : $xml.find("TipoCambio").text();
  var condicionVenta = $xml.find("CondicionVenta");
  var plazoCredito = $xml.find("PlazoCredito").text() === "" ? "0" : $xml.find("PlazoCredito").text();
  var medioPago = $xml.find("MedioPago").first();

  var emisorNombre = $xml.find("Emisor").find("Nombre");
  var emisorNombreComercial = $xml.find("Emisor").find("NombreComercial");
  var tipoCedulaEmisor = $xml.find("Emisor").find("Tipo");
  var emisorNumero = $xml.find("Emisor").find("Numero");
  var CorreoElectronico = $xml.find("Emisor").find("CorreoElectronico");

  var nombreReceptor = $xml.find("Receptor").find("Nombre");
  var nombreComercialReceptor = $xml.find("Receptor").find("NombreComercial");
  var tipoCedulaReceptor = $xml.find("Receptor").find("Tipo");
  var cedulaReceptor = $xml.find("Receptor").find("Numero");

  //Recorro el detalle de servicio.
  var linea = "";
  $xml.find("LineaDetalle").each(function () {

    var NumeroLinea = $(this).find("NumeroLinea").text();
    var CodigoComercial = $(this).find("CodigoComercial").find("Codigo").text() === "" ? "N/A" : $(this).find("CodigoComercial").find("Codigo").text() ;
    var Cantidad = $(this).find("Cantidad").text();
    var UnidadMedida = $(this).find("UnidadMedida").text();
    var Detalle = $(this).find("Detalle").text();
    var PrecioUnitario = $(this).find("PrecioUnitario").text();
    var MontoTotal = $(this).find("MontoTotal").text();
    var SubTotal = $(this).find("SubTotal").text();
    var BaseImponible = $(this).find("BaseImponible").text();
    var ImpuestoNeto = $(this).find("ImpuestoNeto").text();
    var MontoTotalLinea = $(this).find("MontoTotalLinea").text();

    var MontoDescuento = 0;
    var NaturalezaDescuento = "";

    linea += '<tr>';

    $(this).find("Descuento").each(function () {
      MontoDescuento = $(this).find('MontoDescuento').text();
      NaturalezaDescuento = $(this).find('NaturalezaDescuento').text();
    });

    linea += '<td><input class="form-control" type="text" readonly="readonly" name="NumeroLinea[]" value="' + NumeroLinea + '" /></td>';
    linea += '<td><input class="form-control" type="text" readonly="readonly" name="Codigo[]" value="' + CodigoComercial + '" /></td>';
    linea += '<td><input class="form-control" type="text" readonly="readonly" name="Cantidad[]" value="' + Cantidad + '" /></td>';
    linea += '<td><input class="form-control" type="text" readonly="readonly" name="UnidadMedida[]" value="' + UnidadMedida + '" /></td>';
    linea += '<td><input class="form-control" type="text" readonly="readonly" name="Detalle[]" value="' + Detalle + '" /></td>';
    linea += '<td><input class="form-control" type="text" readonly="readonly" name="PrecioUnitario[]" value="' + PrecioUnitario + '" /></td>';
    linea += '<td><input class="form-control" type="text" readonly="readonly" name="MontoTotal[]" value="' + MontoTotal + '" /></td>';
    linea += '<td><input class="form-control" type="text" readonly="readonly" name="SubTotal[]" value="' + SubTotal + '" /></td>';
    linea += '<td><input class="form-control" type="text" readonly="readonly" name="MontoDescuento[]" value="' + MontoDescuento + '" /><input class="form-control" type="hidden" readonly="readonly" name="NaturalezaDescuento[]" value="' + NaturalezaDescuento + '" /></td>';
    linea += '<td>';

    $(this).find("Impuesto").each(function () {

      var Codigo = $(this).find('Codigo').text();
      var CodigoTarifa = $(this).find('CodigoTarifa').text();
      var Tarifa = $(this).find('Tarifa').text();
      var Monto = $(this).find('Monto').text();

      if(Codigo === "01"){
        linea += '<input class="form-control" type="hidden" readonly="readonly" name="iCodigo'+NumeroLinea+'[]" value="' + Codigo + '" />';
        linea += '<input class="form-control" type="hidden" readonly="readonly" name="iCodigoTarifa'+NumeroLinea+'[]" value="' + CodigoTarifa + '" />';
        linea += '<input class="form-control" type="hidden" readonly="readonly" name="iTarifa'+NumeroLinea+'[]" value="' + Tarifa + '" />';
        linea += '<input class="form-control sumarIva" type="text" readonly="readonly" name="iMonto'+NumeroLinea+'[]" value="' + Monto + '" />';
      }else{
        linea += '<input class="form-control" type="hidden" readonly="readonly" name="iCodigo'+NumeroLinea+'[]" value="' + Codigo + '" />';
        linea += '<input class="form-control" type="hidden" readonly="readonly" name="iCodigoTarifa'+NumeroLinea+'[]" value="' + CodigoTarifa + '" />';
        linea += '<input class="form-control" type="hidden" readonly="readonly" name="iTarifa'+NumeroLinea+'[]" value="' + Tarifa + '" />';
        linea += '<input class="form-control restarIva" type="hidden" readonly="readonly" name="iMonto'+NumeroLinea+'[]" value="' + Monto + '" />';
      }

      $(this).find("Exoneracion").each(function (){

        var TipoDocumento = $(this).find('TipoDocumento').text();
        var NumeroDocumento = $(this).find('NumeroDocumento').text();
        var NombreInstitucion = $(this).find('NombreInstitucion').text();
        var FechaEmision = $(this).find('FechaEmision').text();
        var PorcentajeExoneracion = $(this).find('PorcentajeExoneracion').text();
        var MontoExoneracion = $(this).find('MontoExoneracion').text();

        linea += '<input class="form-control" type="hidden" readonly="readonly" name="TipoDocumento'+NumeroLinea+'[]" value="' + TipoDocumento + '" />';
        linea += '<input class="form-control" type="hidden" readonly="readonly" name="NumeroDocumento'+NumeroLinea+'[]" value="' + NumeroDocumento + '" />';
        linea += '<input class="form-control" type="hidden" readonly="readonly" name="NombreInstitucion'+NumeroLinea+'[]" value="' + NombreInstitucion + '" />';
        linea += '<input class="form-control" type="hidden" readonly="readonly" name="FechaEmision'+NumeroLinea+'[]" value="' + FechaEmision + '" />';
        linea += '<input class="form-control" type="hidden" readonly="readonly" name="PorcentajeExoneracion'+NumeroLinea+'[]" value="' + PorcentajeExoneracion + '" />';
        linea += '<input class="form-control" type="hidden" readonly="readonly" name="MontoExoneracion'+NumeroLinea+'[]" value="' + MontoExoneracion + '" />';

      });

    });

    linea += '</td>';
    linea += '<td><input class="form-control" type="text" readonly="readonly" name="ImpuestoNeto[]" value="' + ImpuestoNeto + '" /></td>';
    linea += '<td><input class="form-control" type="text" readonly="readonly" name="MontoTotalLinea[]" value="' + MontoTotalLinea + '" /></td>';


    linea += '</tr>';
  });

  $("#lineasFactura").html(""); //Limpio la tabla
  $("#lineasFactura").append(linea);
  $("#codigoActividad").val(codigoActividad.text());
  $("#clave").val(Clave.text());
  $("#fechaEmision").val(FechaEmision.text());
  $("#moneda").val(CodigoMoneda);
  $("#tipoCambio").val(TipoCambio);
  $("#emisorMr").val(emisorNombre.text());
  $("#identificacionEmisorMr").val(emisorNumero.text());
  $("#nombreComercialEmisorMr").val(emisorNombreComercial.text());
  $("#correoEmisorMr").val(CorreoElectronico.text());
  $("#tipoIdentificacionEmisorMr").val(tipoCedulaEmisor.text());
  $("#nombreReceptorMr").val(nombreReceptor.text());
  $("#nombreComercialReceptorMr").val(nombreComercialReceptor.text());
  $("#tipoIdentificacionReceptorMr").val(tipoCedulaReceptor.text());
  $("#identificacionReceptorMr").val(cedulaReceptor.text());
  $("#condicionVenta").val(condicionVenta.text());
  $("#plazoCredito").val(plazoCredito.text());
  $("#medioPago").val(medioPago.text());

//Totales
  var TotalServGravados = $xml.find("TotalServGravados").text() === "" ? "0.00" : $xml.find("TotalServGravados").text();
  var TotalServExentos = $xml.find("TotalServExentos").text() === "" ? "0.00" : $xml.find("TotalServExentos").text();
  var TotalServExonerado = $xml.find("TotalServExonerado").text() === "" ? "0.00" : $xml.find("TotalServExonerado").text();
  var TotalMercanciasGravadas = $xml.find("TotalMercanciasGravadas").text() === "" ? "0.00" : $xml.find("TotalMercanciasGravadas").text();
  var TotalMercanciasExentas = $xml.find("TotalMercanciasExentas").text() === "" ? "0.00" : $xml.find("TotalMercanciasExentas").text();
  var TotalMercExonerada = $xml.find("TotalMercExonerada").text() === "" ? "0.00" : $xml.find("TotalMercExonerada").text();
  var TotalGravado = $xml.find("TotalGravado").text() === "" ? "0.00" : $xml.find("TotalGravado").text();
  var TotalExento = $xml.find("TotalExento").text() === "" ? "0.00" : $xml.find("TotalExento").text();
  var TotalExonerado = $xml.find("TotalExonerado").text() === "" ? "0.00" : $xml.find("TotalExonerado").text();
  var TotalVenta = $xml.find("TotalVenta").text() === "" ? "0.00" : $xml.find("TotalVenta").text();
  var TotalDescuentos = $xml.find("TotalDescuentos").text() === "" ? "0.00" : $xml.find("TotalDescuentos").text();
  var TotalVentaNeta = $xml.find("TotalVentaNeta").text() === "" ? "0.00" : $xml.find("TotalVentaNeta").text();
  var TotalImpuesto = $xml.find("TotalImpuesto").text() === "" ? "0.00" : $xml.find("TotalImpuesto").text();
  var TotalIVADevuelto = $xml.find("TotalIVADevuelto").text() === "" ? "0.00" : $xml.find("TotalIVADevuelto").text();
  var TotalOtrosCargos = $xml.find("TotalOtrosCargos").text() === "" ? "0.00" : $xml.find("TotalOtrosCargos").text();
  var TotalComprobante = $xml.find("TotalComprobante").text() === "" ? "0.00" : $xml.find("TotalComprobante").text();

  $("#totalServGravados").val(TotalServGravados);
  $("#totalServExentos").val(TotalServExentos);
  $("#totalServExonerado").val(TotalServExonerado);
  $("#totalMercGravadas").val(TotalMercanciasGravadas);
  $("#totalMercExentas").val(TotalMercanciasExentas);
  $("#totalMercExonerada").val(TotalMercExonerada);
  $("#totalGravados").val(TotalGravado);
  $("#totalExentos").val(TotalExento);
  $("#totalExonerado").val(TotalExonerado);
  $("#totalVentas").val(TotalVenta);
  $("#totalDescuentos").val(TotalDescuentos);
  $("#totalVentaNeta").val(TotalVentaNeta);
  $("#impuestos").val(TotalImpuesto);
  $("#totalIVADevuelto").val(TotalIVADevuelto);
  $("#totalOtrosCargos").val(TotalOtrosCargos);
  $("#totalFactura").val(TotalComprobante);

  sumarIva();
  restarAIva();

  setTimeout(function(){
    $("#condicionImpuesto").change();
  }, 400);

}

function readXml(event, out) {

  //Limipo el formulario
  cleanForm();

  var input = event.target;
  var reader = new FileReader();
  reader.onload = function () {
    var text = reader.result;
    var node = document.getElementById(out);
    //node.innerText = text;
    var $xml;
    var xml = text,
        xmlDoc = $.parseXML(xml),
        $xml = $(xmlDoc);
    var codigoActividad = $xml.find("CodigoActividad");
    var Clave = $xml.find("Clave");

    $xml.find("TotalServGravados").text() === "" ? "0.00" : $xml.find("TotalServGravados").text();

    var FechaEmision = $xml.find("FechaEmision").first();
    var CodigoMoneda = $xml.find("CodigoMoneda").text() === "" ? "CRC" : $xml.find("CodigoMoneda").text();
    var TipoCambio = $xml.find("TipoCambio").text() === "" ? "1.00" : $xml.find("TipoCambio").text();
    var condicionVenta = $xml.find("CondicionVenta");
    var plazoCredito = $xml.find("PlazoCredito").text() === "" ? "0" : $xml.find("PlazoCredito").text();
    var medioPago = $xml.find("MedioPago").first();

    var emisorNombre = $xml.find("Emisor").find("Nombre");
    var emisorNombreComercial = $xml.find("Emisor").find("NombreComercial");
    var tipoCedulaEmisor = $xml.find("Emisor").find("Tipo");
    var emisorNumero = $xml.find("Emisor").find("Numero");
    var CorreoElectronico = $xml.find("Emisor").find("CorreoElectronico");

    var nombreReceptor = $xml.find("Receptor").find("Nombre");
    var nombreComercialReceptor = $xml.find("Receptor").find("NombreComercial");
    var tipoCedulaReceptor = $xml.find("Receptor").find("Tipo");
    var cedulaReceptor = $xml.find("Receptor").find("Numero");

    //Recorro el detalle de servicio.
    var linea = "";
    $xml.find("LineaDetalle").each(function () {

      var NumeroLinea = $(this).find("NumeroLinea").text();
      var CodigoComercial = $(this).find("CodigoComercial").find("Codigo").text() === "" ? "N/A" : $(this).find("CodigoComercial").find("Codigo").text() ;
      var Cantidad = $(this).find("Cantidad").text();
      var UnidadMedida = $(this).find("UnidadMedida").text();
      var Detalle = $(this).find("Detalle").text();
      var PrecioUnitario = $(this).find("PrecioUnitario").text();
      var MontoTotal = $(this).find("MontoTotal").text();
      var SubTotal = $(this).find("SubTotal").text();
      var BaseImponible = $(this).find("BaseImponible").text();
      var ImpuestoNeto = $(this).find("ImpuestoNeto").text();
      var MontoTotalLinea = $(this).find("MontoTotalLinea").text();

      var MontoDescuento = 0;
      var NaturalezaDescuento = "";

      linea += '<tr>';

      $(this).find("Descuento").each(function () {
        MontoDescuento = $(this).find('MontoDescuento').text();
        NaturalezaDescuento = $(this).find('NaturalezaDescuento').text();
      });

      linea += '<td><input class="form-control" type="text" readonly="readonly" name="NumeroLinea[]" value="' + NumeroLinea + '" /></td>';
      linea += '<td><input class="form-control" type="text" readonly="readonly" name="Codigo[]" value="' + CodigoComercial + '" /></td>';
      linea += '<td><input class="form-control" type="text" readonly="readonly" name="Cantidad[]" value="' + Cantidad + '" /></td>';
      linea += '<td><input class="form-control" type="text" readonly="readonly" name="UnidadMedida[]" value="' + UnidadMedida + '" /></td>';
      linea += '<td><input class="form-control" type="text" readonly="readonly" name="Detalle[]" value="' + Detalle + '" /></td>';
      linea += '<td><input class="form-control" type="text" readonly="readonly" name="PrecioUnitario[]" value="' + PrecioUnitario + '" /></td>';
      linea += '<td><input class="form-control" type="text" readonly="readonly" name="MontoTotal[]" value="' + MontoTotal + '" /></td>';
      linea += '<td><input class="form-control" type="text" readonly="readonly" name="SubTotal[]" value="' + SubTotal + '" /></td>';
      linea += '<td><input class="form-control" type="text" readonly="readonly" name="MontoDescuento[]" value="' + MontoDescuento + '" /><input class="form-control" type="hidden" readonly="readonly" name="NaturalezaDescuento[]" value="' + NaturalezaDescuento + '" /></td>';
      linea += '<td>';

      $(this).find("Impuesto").each(function () {

        var Codigo = $(this).find('Codigo').text();
        var CodigoTarifa = $(this).find('CodigoTarifa').text();
        var Tarifa = $(this).find('Tarifa').text();
        var Monto = $(this).find('Monto').text();

        if(Codigo === "01"){
          linea += '<input class="form-control" type="hidden" readonly="readonly" name="iCodigo'+NumeroLinea+'[]" value="' + Codigo + '" />';
          linea += '<input class="form-control" type="hidden" readonly="readonly" name="iCodigoTarifa'+NumeroLinea+'[]" value="' + CodigoTarifa + '" />';
          linea += '<input class="form-control" type="hidden" readonly="readonly" name="iTarifa'+NumeroLinea+'[]" value="' + Tarifa + '" />';
          linea += '<input class="form-control sumarIva" type="text" readonly="readonly" name="iMonto'+NumeroLinea+'[]" value="' + Monto + '" />';
        }else{
          linea += '<input class="form-control" type="hidden" readonly="readonly" name="iCodigo'+NumeroLinea+'[]" value="' + Codigo + '" />';
          linea += '<input class="form-control" type="hidden" readonly="readonly" name="iCodigoTarifa'+NumeroLinea+'[]" value="' + CodigoTarifa + '" />';
          linea += '<input class="form-control" type="hidden" readonly="readonly" name="iTarifa'+NumeroLinea+'[]" value="' + Tarifa + '" />';
          linea += '<input class="form-control restarIva" type="hidden" readonly="readonly" name="iMonto'+NumeroLinea+'[]" value="' + Monto + '" />';
        }

        $(this).find("Exoneracion").each(function (){

          var TipoDocumento = $(this).find('TipoDocumento').text();
          var NumeroDocumento = $(this).find('NumeroDocumento').text();
          var NombreInstitucion = $(this).find('NombreInstitucion').text();
          var FechaEmision = $(this).find('FechaEmision').text();
          var PorcentajeExoneracion = $(this).find('PorcentajeExoneracion').text();
          var MontoExoneracion = $(this).find('MontoExoneracion').text();

          linea += '<input class="form-control" type="hidden" readonly="readonly" name="TipoDocumento'+NumeroLinea+'[]" value="' + TipoDocumento + '" />';
          linea += '<input class="form-control" type="hidden" readonly="readonly" name="NumeroDocumento'+NumeroLinea+'[]" value="' + NumeroDocumento + '" />';
          linea += '<input class="form-control" type="hidden" readonly="readonly" name="NombreInstitucion'+NumeroLinea+'[]" value="' + NombreInstitucion + '" />';
          linea += '<input class="form-control" type="hidden" readonly="readonly" name="FechaEmision'+NumeroLinea+'[]" value="' + FechaEmision + '" />';
          linea += '<input class="form-control" type="hidden" readonly="readonly" name="PorcentajeExoneracion'+NumeroLinea+'[]" value="' + PorcentajeExoneracion + '" />';
          linea += '<input class="form-control" type="hidden" readonly="readonly" name="MontoExoneracion'+NumeroLinea+'[]" value="' + MontoExoneracion + '" />';

        });

      });

      linea += '</td>';
      linea += '<td><input class="form-control" type="text" readonly="readonly" name="ImpuestoNeto[]" value="' + ImpuestoNeto + '" /></td>';
      linea += '<td><input class="form-control" type="text" readonly="readonly" name="MontoTotalLinea[]" value="' + MontoTotalLinea + '" /></td>';


      linea += '</tr>';
    });

    $("#lineasFactura").html(""); //Limpio la tabla
    $("#lineasFactura").append(linea);
    $("#codigoActividad").val(codigoActividad.text());
    $("#clave").val(Clave.text());
    $("#fechaEmision").val(FechaEmision.text());
    $("#moneda").val(CodigoMoneda);
    $("#tipoCambio").val(TipoCambio);
    $("#emisorMr").val(emisorNombre.text());
    $("#identificacionEmisorMr").val(emisorNumero.text());
    $("#nombreComercialEmisorMr").val(emisorNombreComercial.text());
    $("#correoEmisorMr").val(CorreoElectronico.text());
    $("#tipoIdentificacionEmisorMr").val(tipoCedulaEmisor.text());
    $("#nombreReceptorMr").val(nombreReceptor.text());
    $("#nombreComercialReceptorMr").val(nombreComercialReceptor.text());
    $("#tipoIdentificacionReceptorMr").val(tipoCedulaReceptor.text());
    $("#identificacionReceptorMr").val(cedulaReceptor.text());
    $("#condicionVenta").val(condicionVenta.text());
    $("#plazoCredito").val(plazoCredito.text());
    $("#medioPago").val(medioPago.text());

    //Totales
    var TotalServGravados = $xml.find("TotalServGravados").text() === "" ? "0.00" : $xml.find("TotalServGravados").text();
    var TotalServExentos = $xml.find("TotalServExentos").text() === "" ? "0.00" : $xml.find("TotalServExentos").text();
    var TotalServExonerado = $xml.find("TotalServExonerado").text() === "" ? "0.00" : $xml.find("TotalServExonerado").text();
    var TotalMercanciasGravadas = $xml.find("TotalMercanciasGravadas").text() === "" ? "0.00" : $xml.find("TotalMercanciasGravadas").text();
    var TotalMercanciasExentas = $xml.find("TotalMercanciasExentas").text() === "" ? "0.00" : $xml.find("TotalMercanciasExentas").text();
    var TotalMercExonerada = $xml.find("TotalMercExonerada").text() === "" ? "0.00" : $xml.find("TotalMercExonerada").text();
    var TotalGravado = $xml.find("TotalGravado").text() === "" ? "0.00" : $xml.find("TotalGravado").text();
    var TotalExento = $xml.find("TotalExento").text() === "" ? "0.00" : $xml.find("TotalExento").text();
    var TotalExonerado = $xml.find("TotalExonerado").text() === "" ? "0.00" : $xml.find("TotalExonerado").text();
    var TotalVenta = $xml.find("TotalVenta").text() === "" ? "0.00" : $xml.find("TotalVenta").text();
    var TotalDescuentos = $xml.find("TotalDescuentos").text() === "" ? "0.00" : $xml.find("TotalDescuentos").text();
    var TotalVentaNeta = $xml.find("TotalVentaNeta").text() === "" ? "0.00" : $xml.find("TotalVentaNeta").text();
    var TotalImpuesto = $xml.find("TotalImpuesto").text() === "" ? "0.00" : $xml.find("TotalImpuesto").text();
    var TotalIVADevuelto = $xml.find("TotalIVADevuelto").text() === "" ? "0.00" : $xml.find("TotalIVADevuelto").text();
    var TotalOtrosCargos = $xml.find("TotalOtrosCargos").text() === "" ? "0.00" : $xml.find("TotalOtrosCargos").text();
    var TotalComprobante = $xml.find("TotalComprobante").text() === "" ? "0.00" : $xml.find("TotalComprobante").text();

    $("#totalServGravados").val(TotalServGravados);
    $("#totalServExentos").val(TotalServExentos);
    $("#totalServExonerado").val(TotalServExonerado);
    $("#totalMercGravadas").val(TotalMercanciasGravadas);
    $("#totalMercExentas").val(TotalMercanciasExentas);
    $("#totalMercExonerada").val(TotalMercExonerada);
    $("#totalGravados").val(TotalGravado);
    $("#totalExentos").val(TotalExento);
    $("#totalExonerado").val(TotalExonerado);
    $("#totalVentas").val(TotalVenta);
    $("#totalDescuentos").val(TotalDescuentos);
    $("#totalVentaNeta").val(TotalVentaNeta);
    $("#impuestos").val(TotalImpuesto);
    $("#totalIVADevuelto").val(TotalIVADevuelto);
    $("#totalOtrosCargos").val(TotalOtrosCargos);
    $("#totalFactura").val(TotalComprobante);

    sumarIva();
    restarAIva();

    setTimeout(function(){
      $("#condicionImpuesto").change();
    }, 400);

  };
  reader.readAsText(input.files[0]);
}

function sumarIva(){
  var total = 0;
  $('.sumarIva').each(function(){ total += parseFloat($(this).val()); });
  $("#ivaTotal").val(total);
}

function restarAIva(){
  var totalRestar = 0;
  $('.restarIva').each(function(){ totalRestar += parseFloat($(this).val()); });
  $("#restarAIvaTotal").val(totalRestar);
}