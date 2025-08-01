/**
 * Mensaje Receptor Inbox - JavaScript
 * Manejo de funcionalidades e interacciones
 */

// Variables globales
let currentPage = 0;
let totalPages = 0;
let currentFilter = 'P'; // Por defecto mostrar pendientes

// Inicialización cuando el DOM está listo
document.addEventListener('DOMContentLoaded', function() {
  initializeMRInbox();
});

/**
 * Inicializa todas las funcionalidades del MR Inbox
 */
function initializeMRInbox() {
  // Inicializar tooltips
  initializeTooltips();

  // Configurar botones de acción
  setupActionButtons();

  // Configurar búsqueda en tiempo real
  setupSearchBox();

  // Configurar filtros
  setupFilters();

  // Cargar estadísticas
  loadMRStats();

  // Configurar acciones de tabla
  setupTableActions();

  // Auto-refresh cada 5 minutos para documentos pendientes
  if (getUrlParam('e') === 'P' || !getUrlParam('e')) {
    setInterval(refreshIfPending, 300000); // 5 minutos
  }
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
  // Botón actualizar
  const btnRefresh = document.getElementById('btnRefreshMR');
  if (btnRefresh) {
    btnRefresh.addEventListener('click', function() {
      refreshMRList();
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
 * Realiza la búsqueda de documentos
 */
function performSearch(query) {
  const estado = document.getElementById('filtroEstado').value;

  // Si la búsqueda está vacía, recargar con el filtro actual
  if (!query) {
    window.location.href = `/mensaje-receptor-inbox/?e=${estado}`;
    return;
  }

  // Realizar búsqueda con el query y estado
  window.location.href = `/mensaje-receptor-inbox/?e=${estado}&q=${encodeURIComponent(query)}`;
}

/**
 * Configura los filtros
 */
function setupFilters() {
  const filtroEstado = document.getElementById('filtroEstado');
  if (filtroEstado) {
    // Establecer el valor actual del filtro
    const currentEstado = getUrlParam('e') || 'P';
    filtroEstado.value = currentEstado;
  }
}

/**
 * Filtra por estado
 */
function filtrarPorEstado() {
  const estado = document.getElementById('filtroEstado').value;
  const query = getUrlParam('q');

  let url = `/mensaje-receptor-inbox/?e=${estado}`;
  if (query) {
    url += `&q=${encodeURIComponent(query)}`;
  }

  window.location.href = url;
}

/**
 * Carga las estadísticas de MR
 */
function loadMRStats() {
  // Animar contadores
  animateStatCounters();
}

/**
 * Anima los contadores de estadísticas
 */
function animateStatCounters() {
  const statValues = document.querySelectorAll('.stat-value');

  statValues.forEach(stat => {
    // Solo animar números, no montos
    if (!stat.textContent.includes('₡')) {
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
    }
  });
}

/**
 * Configura las acciones de la tabla
 */
function setupTableActions() {
  // Manejar clics en filas para expandir detalles
  const tableRows = document.querySelectorAll('.modern-table tbody tr');
  tableRows.forEach(row => {
    row.addEventListener('click', function(e) {
      // No expandir si se hace clic en un botón o enlace
      if (e.target.closest('.action-buttons') || e.target.closest('.document-links')) return;

      // Toggle clase de selección
      this.classList.toggle('selected');
    });
  });
}

/**
 * Ver detalle del MR
 */
function verDetalleMR(id) {
  // Mostrar loading
  showLoading();

  // Realizar petición AJAX
  fetch(`/mensaje-receptor-inbox/detalle/${id}`)
  .then(response => response.json())
  .then(data => {
    hideLoading();
    showMRDetail(data);
  })
  .catch(error => {
    hideLoading();
    showError('Error al cargar el detalle del documento');
    console.error('Error:', error);
  });
}

/**
 * Muestra el detalle del MR en el modal
 */
function showMRDetail(data) {
  const modalContent = document.getElementById('detalleMRContent');

  const html = `
        <div class="mr-detail">
            <div class="detail-header">
                <h4>Documento: ${data.tipoDocumento}</h4>
                <span class="badge ${data.estado === 'P' ? 'badge-warning' : 'badge-success'}">
                    ${data.estado === 'P' ? 'Pendiente' : 'Aplicado'}
                </span>
            </div>
            
            <div class="detail-section">
                <h5>Información del Emisor</h5>
                <div class="row">
                    <div class="col-md-6">
                        <div class="detail-group">
                            <label>Nombre:</label>
                            <p>${data.emisorFactura}</p>
                        </div>
                        
                        <div class="detail-group">
                            <label>Identificación:</label>
                            <p>${data.emisorIdentificacion}</p>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="detail-group">
                            <label>Correo:</label>
                            <p>${data.correoEmisor || 'No especificado'}</p>
                        </div>
                        
                        <div class="detail-group">
                            <label>Teléfono:</label>
                            <p>${data.telefonoEmisor || 'No especificado'}</p>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="detail-section">
                <h5>Información del Documento</h5>
                <div class="row">
                    <div class="col-md-6">
                        <div class="detail-group">
                            <label>Clave:</label>
                            <p class="small font-monospace">${data.clave}</p>
                        </div>
                        
                        <div class="detail-group">
                            <label>Fecha Emisión:</label>
                            <p>${formatDateTime(data.fechaEmision)}</p>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="detail-group">
                            <label>Total Impuestos:</label>
                            <p class="font-weight-bold">${data.moneda} ${formatNumber(data.totalImpuestos)}</p>
                        </div>
                        
                        <div class="detail-group">
                            <label>Total Documento:</label>
                            <p class="font-weight-bold text-primary">${data.moneda} ${formatNumber(data.totalComprobante)}</p>
                        </div>
                    </div>
                </div>
            </div>
            
            ${data.estado === 'A' ? `
            <div class="detail-section">
                <h5>Información de Aplicación</h5>
                <div class="detail-group">
                    <label>Fecha de Aplicación:</label>
                    <p>${formatDateTime(data.fechaAplicacion)}</p>
                </div>
                
                <div class="detail-group">
                    <label>Aplicado por:</label>
                    <p>${data.usuarioAplico || 'Sistema'}</p>
                </div>
                
                <div class="detail-group">
                    <label>Consecutivo MR Generado:</label>
                    <p>${data.consecutivoMR || 'N/A'}</p>
                </div>
            </div>
            ` : ''}
            
            <div class="mt-4 text-right">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                ${data.estado === 'P' ? `
                <a href="/mensaje-receptor-inbox/form/${data.id}" class="btn btn-primary">
                    <i class="fas fa-cogs"></i> Procesar
                </a>
                ` : ''}
            </div>
        </div>
    `;

  modalContent.innerHTML = html;
  $('#modalDetalleMR').modal('show');
}

/**
 * Ver historial del MR
 */
function verHistorialMR(id) {
  // Mostrar loading
  showLoading();

  // Realizar petición AJAX para obtener historial
  fetch(`/mensaje-receptor-inbox/historial/${id}`)
  .then(response => response.json())
  .then(data => {
    hideLoading();
    showMRHistory(data);
  })
  .catch(error => {
    hideLoading();
    showError('Error al cargar el historial del documento');
    console.error('Error:', error);
  });
}

/**
 * Muestra el historial del MR
 */
function showMRHistory(data) {
  // Implementar cuando tengas el endpoint del historial
  Swal.fire({
    icon: 'info',
    title: 'Historial',
    text: 'Funcionalidad en desarrollo',
    confirmButtonColor: '#667eea'
  });
}

/**
 * Actualiza la lista de MR
 */
function refreshMRList() {
  showLoading('Actualizando documentos...');
  location.reload();
}

/**
 * Auto-refresh si hay documentos pendientes
 */
function refreshIfPending() {
  const pendingBadges = document.querySelectorAll('.estado-pendiente');
  if (pendingBadges.length > 0) {
    console.log('Actualizando documentos pendientes...');
    location.reload();
  }
}

/**
 * Obtiene un parámetro de la URL
 */
function getUrlParam(param) {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get(param);
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
 * Formatea una fecha y hora
 */
function formatDateTime(dateString) {
  if (!dateString) return 'N/A';

  // Si contiene 'T', es formato ISO
  if (dateString.includes('T')) {
    const [date, time] = dateString.split('T');
    const [year, month, day] = date.split('-');
    const formattedDate = `${day}/${month}/${year}`;
    const formattedTime = time.substring(0, 5); // HH:MM
    return `${formattedDate} ${formattedTime}`;
  }

  // Si no, intentar parsearlo como fecha normal
  try {
    const date = new Date(dateString);
    return new Intl.DateTimeFormat('es-CR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    }).format(date);
  } catch (e) {
    return dateString;
  }
}

// Exportar funciones para uso global
window.verDetalleMR = verDetalleMR;
window.verHistorialMR = verHistorialMR;
window.filtrarPorEstado = filtrarPorEstado;