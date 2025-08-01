/**
 * Catálogo de Clientes - JavaScript
 * Manejo de funcionalidades e interacciones
 */

// Variables globales
let currentPage = 0;
let totalPages = 0;
let selectedClients = [];

// Inicialización cuando el DOM está listo
document.addEventListener('DOMContentLoaded', function() {
  initializeClientCatalog();
});

/**
 * Inicializa todas las funcionalidades del catálogo
 */
function initializeClientCatalog() {
  // Inicializar tooltips
  initializeTooltips();

  // Configurar botones de acción
  setupActionButtons();

  // Configurar búsqueda en tiempo real
  setupSearchBox();

  // Configurar filtros
  setupFilters();

  // Cargar estadísticas
  loadClientStats();

  // Configurar acciones de tabla
  setupTableActions();

  // Configurar validación de teléfonos
  setupPhoneValidation();
}

/**
 * Inicializa los tooltips de Bootstrap
 */
function initializeTooltips() {
  $('[data-toggle="tooltip"]').tooltip();
}

/**
 * Configura los botones de acción principales
 */
function setupActionButtons() {
  // Botón importar clientes
  const btnImportar = document.getElementById('btnImportarClientes');
  if (btnImportar) {
    btnImportar.addEventListener('click', function() {
      showImportModal();
    });
  }

  // Botón exportar clientes
  const btnExportar = document.getElementById('btnExportarClientes');
  if (btnExportar) {
    btnExportar.addEventListener('click', function() {
      exportClients();
    });
  }

  // Botón actualizar
  const btnRefresh = document.getElementById('btnRefresh');
  if (btnRefresh) {
    btnRefresh.addEventListener('click', function() {
      refreshClientList();
    });
  }

  // Botón filtros
  const btnFilter = document.getElementById('btnFilter');
  if (btnFilter) {
    btnFilter.addEventListener('click', function() {
      toggleFilterPanel();
    });
  }
}

/**
 * Configura la búsqueda en tiempo real
 */
function setupSearchBox() {
  const searchInput = document.querySelector('.search-input');
  if (searchInput) {
    let searchTimeout;

    searchInput.addEventListener('input', function(e) {
      clearTimeout(searchTimeout);
      const searchValue = e.target.value;

      // Esperar 500ms después de que el usuario deje de escribir
      searchTimeout = setTimeout(() => {
        if (searchValue.length >= 3 || searchValue.length === 0) {
          performSearch(searchValue);
        }
      }, 500);
    });

    // Manejar el evento Enter
    searchInput.addEventListener('keypress', function(e) {
      if (e.key === 'Enter') {
        e.preventDefault();
        clearTimeout(searchTimeout);
        performSearch(e.target.value);
      }
    });
  }
}

/**
 * Realiza la búsqueda de clientes
 */
function performSearch(query) {
  // Si la búsqueda está vacía, recargar la página sin parámetros
  if (!query) {
    window.location.href = '/clientes/';
    return;
  }

  // Realizar búsqueda con el query
  window.location.href = `/clientes/?q=${encodeURIComponent(query)}`;
}

/**
 * Configura el panel de filtros
 */
function setupFilters() {
  // Placeholder para implementar filtros avanzados
  console.log('Filtros configurados');
}

/**
 * Carga las estadísticas de clientes
 */
function loadClientStats() {
  // Animar contadores
  animateStatCounters();
}

/**
 * Anima los contadores de estadísticas
 */
function animateStatCounters() {
  const statValues = document.querySelectorAll('.stat-value');

  statValues.forEach(stat => {
    const finalValue = parseInt(stat.textContent);
    let currentValue = 0;
    const increment = Math.ceil(finalValue / 30);
    const timer = setInterval(() => {
      currentValue += increment;
      if (currentValue >= finalValue) {
        currentValue = finalValue;
        clearInterval(timer);
      }
      stat.textContent = currentValue.toLocaleString();
    }, 50);
  });
}

/**
 * Configura las acciones de la tabla
 */
function setupTableActions() {
  // Manejar selección de filas
  const tableRows = document.querySelectorAll('.modern-table tbody tr');
  tableRows.forEach(row => {
    row.addEventListener('click', function(e) {
      // No seleccionar si se hace clic en un botón
      if (e.target.closest('.action-buttons')) return;

      this.classList.toggle('selected');
    });
  });
}

/**
 * Ver detalle del cliente
 */
function verDetalleCliente(id) {
  // Mostrar loading
  showLoading();

  // Realizar petición AJAX
  fetch(`/clientes/detalle/${id}`)
  .then(response => response.json())
  .then(data => {
    hideLoading();
    showClientDetail(data);
  })
  .catch(error => {
    hideLoading();
    showError('Error al cargar el detalle del cliente');
    console.error('Error:', error);
  });
}

/**
 * Muestra el detalle del cliente en el modal
 */
function showClientDetail(client) {
  const modalContent = document.getElementById('detalleClienteContent');

  const html = `
        <div class="client-detail">
            <div class="detail-header">
                <div>
                    <h4>${client.nombreCompleto}</h4>
                    ${client.nombreComercial ? `<p class="text-muted">${client.nombreComercial}</p>` : ''}
                </div>
                <span class="badge badge-success">
                    Activo
                </span>
            </div>
            
            <div class="row">
                <div class="col-md-6">
                    <div class="detail-group">
                        <label>Tipo de Identificación:</label>
                        <p>${client.tipoDeIdentificacion?.nombre || 'No especificado'}</p>
                    </div>
                    
                    <div class="detail-group">
                        <label>Identificación:</label>
                        <p>${client.identificacion}</p>
                    </div>
                    
                    <div class="detail-group">
                        <label>Correo Principal:</label>
                        <p>${client.correo1 || 'No registrado'}</p>
                    </div>
                    
                    <div class="detail-group">
                        <label>Correo Secundario:</label>
                        <p>${client.correo2 || 'No registrado'}</p>
                    </div>
                </div>
                
                <div class="col-md-6">
                    <div class="detail-group">
                        <label>Teléfono Principal:</label>
                        <p>${client.telefono1 ? `+${client.codigoPais} ${client.telefono1}` : 'No registrado'}</p>
                    </div>
                    
                    <div class="detail-group">
                        <label>Teléfono Secundario:</label>
                        <p>${client.telefono2 || 'No registrado'}</p>
                    </div>
                    
                    <div class="detail-group">
                        <label>Provincia:</label>
                        <p>${client.provincia?.nombre || 'No especificada'}</p>
                    </div>
                    
                    <div class="detail-group">
                        <label>Tiene Crédito:</label>
                        <p>
                            ${client.estadoCredito === 'A' ?
      '<span class="badge badge-success">Sí</span>' :
      '<span class="badge badge-danger">No</span>'}
                        </p>
                    </div>
                </div>
            </div>
            
            <div class="detail-group">
                <label>Dirección:</label>
                <p>${formatAddress(client) || 'No registrada'}</p>
            </div>
            
            <div class="detail-group">
                <label>Fecha de Registro:</label>
                <p>${formatDate(client.fechaCreacion)}</p>
            </div>
            
            <div class="mt-4 text-right">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                <a href="/clientes/edit/${client.id}" class="btn btn-primary">
                    <i class="fas fa-edit"></i> Editar
                </a>
            </div>
        </div>
    `;

  modalContent.innerHTML = html;
  $('#modalDetalleCliente').modal('show');
}

/**
 * Ver historial del cliente
 */
function verHistorialCliente(id) {
  // Mostrar loading
  showLoading();

  // Realizar petición AJAX para obtener historial
  fetch(`/clientes/historial/${id}`)
  .then(response => response.json())
  .then(data => {
    hideLoading();
    showClientHistory(data);
  })
  .catch(error => {
    hideLoading();
    showError('Error al cargar el historial del cliente');
    console.error('Error:', error);
  });
}

/**
 * Muestra el historial del cliente
 */
function showClientHistory(data) {
  const modalContent = document.getElementById('historialClienteContent');

  let tableRows = '';
  let total = 0;

  if (data.facturas && data.facturas.length > 0) {
    data.facturas.forEach(factura => {
      total += factura.totalComprobante;
      tableRows += `
                <tr>
                    <td>${formatDate(factura.fecha)}</td>
                    <td>${factura.consecutivo}</td>
                    <td>${factura.tipoDocumento}</td>
                    <td class="text-right">${factura.moneda.simboloMoneda} ${formatNumber(factura.totalComprobante)}</td>
                    <td>
                        <span class="badge badge-${getEstadoBadgeClass(factura.estado)}">
                            ${factura.estado}
                        </span>
                    </td>
                    <td>
                        <a href="/fe-documentos/ver/${factura.id}" class="btn btn-sm btn-info" target="_blank">
                            <i class="fas fa-eye"></i>
                        </a>
                    </td>
                </tr>
            `;
    });
  } else {
    tableRows = '<tr><td colspan="6" class="text-center">No hay facturas registradas</td></tr>';
  }

  const html = `
        <div class="history-container">
            <div class="mb-3">
                <h5>${data.cliente.nombreCompleto}</h5>
                <p class="text-muted">Historial de compras y documentos</p>
            </div>
            
            <div class="table-responsive">
                <table class="table history-table">
                    <thead>
                        <tr>
                            <th>Fecha</th>
                            <th>Número</th>
                            <th>Tipo</th>
                            <th class="text-right">Monto</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${tableRows}
                    </tbody>
                    ${data.facturas && data.facturas.length > 0 ? `
                    <tfoot>
                        <tr class="total-row">
                            <td colspan="3" class="text-right">Total:</td>
                            <td class="text-right">₡ ${formatNumber(total)}</td>
                            <td colspan="2"></td>
                        </tr>
                    </tfoot>
                    ` : ''}
                </table>
            </div>
            
            <div class="mt-4 text-right">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                ${data.facturas && data.facturas.length > 0 ? `
                <button type="button" class="btn btn-primary" onclick="exportarHistorial(${data.cliente.id})">
                    <i class="fas fa-file-excel"></i> Exportar a Excel
                </button>
                ` : ''}
            </div>
        </div>
    `;

  modalContent.innerHTML = html;
  $('#modalHistorialCliente').modal('show');
}

/**
 * Confirma la eliminación de un cliente
 */
function confirmarEliminarCliente(id) {
  Swal.fire({
    title: '¿Estás seguro?',
    text: 'Esta acción eliminará el cliente permanentemente.',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#f56565',
    cancelButtonColor: '#718096',
    confirmButtonText: 'Sí, eliminar',
    cancelButtonText: 'Cancelar'
  }).then((result) => {
    if (result.isConfirmed) {
      eliminarCliente(id);
    }
  });
}

/**
 * Elimina un cliente
 */
function eliminarCliente(id) {
  showLoading();

  fetch(`/clientes/delete/${id}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'X-CSRF-TOKEN': getCsrfToken()
    }
  })
  .then(response => response.json())
  .then(data => {
    hideLoading();
    if (data.response === 1) {
      Swal.fire({
        icon: 'success',
        title: 'Cliente eliminado',
        text: 'El cliente ha sido eliminado correctamente',
        timer: 2000,
        showConfirmButton: false
      }).then(() => {
        location.reload();
      });
    } else if (data.response === 2) {
      showError(data.msj || 'Este registro tiene facturas ligadas, no puede eliminarlo');
    } else {
      showError('Error al eliminar el cliente');
    }
  })
  .catch(error => {
    hideLoading();
    showError('Error al procesar la solicitud');
    console.error('Error:', error);
  });
}

/**
 * Muestra el modal de importación
 */
function showImportModal() {
  Swal.fire({
    title: 'Importar Clientes',
    html: `
            <div class="import-modal-content">
                <p>Selecciona un archivo Excel o CSV con los clientes a importar:</p>
                <input type="file" id="importFile" class="swal2-input" accept=".xlsx,.xls,.csv">
                <div class="mt-3">
                    <a href="/clientes/plantilla-importacion" class="btn btn-sm btn-info">
                        <i class="fas fa-download"></i> Descargar plantilla
                    </a>
                </div>
            </div>
        `,
    showCancelButton: true,
    confirmButtonText: 'Importar',
    cancelButtonText: 'Cancelar',
    preConfirm: () => {
      const file = document.getElementById('importFile').files[0];
      if (!file) {
        Swal.showValidationMessage('Por favor selecciona un archivo');
        return false;
      }
      return file;
    }
  }).then((result) => {
    if (result.isConfirmed) {
      performImport(result.value);
    }
  });
}

/**
 * Realiza la importación de clientes
 */
function performImport(file) {
  const formData = new FormData();
  formData.append('file', file);

  showLoading('Importando clientes...');

  fetch('/clientes/import', {
    method: 'POST',
    headers: {
      'X-CSRF-TOKEN': getCsrfToken()
    },
    body: formData
  })
  .then(response => response.json())
  .then(data => {
    hideLoading();
    if (data.success) {
      Swal.fire({
        icon: 'success',
        title: 'Importación exitosa',
        html: `Se importaron ${data.imported} clientes correctamente`,
        confirmButtonText: 'Aceptar'
      }).then(() => {
        location.reload();
      });
    } else {
      showError(data.message || 'Error al importar clientes');
    }
  })
  .catch(error => {
    hideLoading();
    showError('Error al procesar la importación');
    console.error('Error:', error);
  });
}

/**
 * Exporta los clientes
 */
function exportClients() {
  const format = 'xlsx'; // Puedes hacer esto dinámico
  window.location.href = `/clientes/export?format=${format}`;
}

/**
 * Exporta el historial de un cliente
 */
function exportarHistorial(clienteId) {
  window.location.href = `/clientes/historial/export/${clienteId}`;
}

/**
 * Actualiza la lista de clientes
 */
function refreshClientList() {
  showLoading('Actualizando lista...');
  location.reload();
}

/**
 * Muestra/oculta el panel de filtros
 */
function toggleFilterPanel() {
  // Implementar cuando tengas el panel de filtros
  console.log('Toggle filter panel');
}

/**
 * Configura la validación de teléfonos
 */
function setupPhoneValidation() {
  // Implementar validación de formato de teléfono si es necesario
  console.log('Validación de teléfonos configurada');
}

// Funciones de utilidad

/**
 * Obtiene el token CSRF
 */
function getCsrfToken() {
  const token = document.querySelector('meta[name="_csrf"]');
  return token ? token.getAttribute('content') : '';
}

/**
 * Muestra un loading
 */
function showLoading(message = 'Cargando...') {
  Swal.fire({
    title: message,
    allowOutsideClick: false,
    showConfirmButton: false,
    willOpen: () => {
      Swal.showLoading();
    }
  });
}

/**
 * Oculta el loading
 */
function hideLoading() {
  Swal.close();
}

/**
 * Muestra un mensaje de error
 */
function showError(message) {
  Swal.fire({
    icon: 'error',
    title: 'Error',
    text: message,
    confirmButtonColor: '#f56565'
  });
}

/**
 * Formatea un número con el formato decimal correcto
 */
function formatNumber(amount) {
  if (!amount || isNaN(amount)) return '0,00';
  return amount.toFixed(2).replace('.', ',').replace(/\B(?=(\d{3})+(?!\d))/g, '.');
}

/**
 * Formatea una fecha
 */
function formatDate(dateString) {
  if (!dateString) return 'N/A';

  const date = new Date(dateString);
  return new Intl.DateTimeFormat('es-CR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  }).format(date);
}

/**
 * Formatea la dirección del cliente
 */
function formatAddress(client) {
  const parts = [];

  if (client.provincia) parts.push(client.provincia.nombre);
  if (client.canton) parts.push(client.canton);
  if (client.distrito) parts.push(client.distrito);
  if (client.otrasSenas) parts.push(client.otrasSenas);

  return parts.join(', ');
}

/**
 * Obtiene la clase del badge según el estado
 */
function getEstadoBadgeClass(estado) {
  const estadoClasses = {
    'aceptado': 'success',
    'rechazado': 'danger',
    'pendiente': 'warning',
    'anulado': 'secondary'
  };

  return estadoClasses[estado.toLowerCase()] || 'info';
}

// Exportar funciones para uso global
window.verDetalleCliente = verDetalleCliente;
window.verHistorialCliente = verHistorialCliente;
window.confirmarEliminarCliente = confirmarEliminarCliente;
window.exportarHistorial = exportarHistorial;