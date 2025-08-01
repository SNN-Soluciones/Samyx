/**
 * Catálogo de Productos - JavaScript
 * Manejo de funcionalidades e interacciones
 */

// Variables globales
let currentPage = 0;
let totalPages = 0;
let selectedProducts = [];

// Inicialización cuando el DOM está listo
document.addEventListener('DOMContentLoaded', function() {
  initializeProductCatalog();
});

/**
 * Inicializa todas las funcionalidades del catálogo
 */
function initializeProductCatalog() {
  // Inicializar tooltips
  initializeTooltips();

  // Configurar botones de acción
  setupActionButtons();

  // Configurar búsqueda en tiempo real
  setupSearchBox();

  // Configurar filtros
  setupFilters();

  // Cargar estadísticas
  loadProductStats();

  // Configurar acciones de tabla
  setupTableActions();

  // Configurar paginación
  setupPagination();
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
  // Botón importar productos
  const btnImportar = document.getElementById('btnImportarProductos');
  if (btnImportar) {
    btnImportar.addEventListener('click', function() {
      showImportModal();
    });
  }

  // Botón exportar productos
  const btnExportar = document.getElementById('btnExportarProductos');
  if (btnExportar) {
    btnExportar.addEventListener('click', function() {
      exportProducts();
    });
  }

  // Botón actualizar
  const btnRefresh = document.getElementById('btnRefresh');
  if (btnRefresh) {
    btnRefresh.addEventListener('click', function() {
      refreshProductList();
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
 * Realiza la búsqueda de productos
 */
function performSearch(query) {
  // Si la búsqueda está vacía, recargar la página sin parámetros
  if (!query) {
    window.location.href = '/productos/';
    return;
  }

  // Realizar búsqueda con el query
  window.location.href = `/productos/?q=${encodeURIComponent(query)}`;
}

/**
 * Configura el panel de filtros
 */
function setupFilters() {
  // Este es un placeholder para cuando implementes filtros avanzados
  console.log('Filtros configurados');
}

/**
 * Carga las estadísticas de productos
 */
function loadProductStats() {
  // Simular carga de estadísticas con animación
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
 * Ver detalle del producto
 */
function verDetalleProducto(id) {
  // Mostrar loading
  showLoading();

  // Realizar petición AJAX
  fetch(`/productos/detalle/${id}`)
  .then(response => response.json())
  .then(data => {
    hideLoading();
    showProductDetail(data);
  })
  .catch(error => {
    hideLoading();
    showError('Error al cargar el detalle del producto');
    console.error('Error:', error);
  });
}

/**
 * Muestra el detalle del producto en el modal
 */
function showProductDetail(product) {
  const modalContent = document.getElementById('detalleProductoContent');

  const html = `
        <div class="product-detail">
            <div class="detail-header">
                <h4>${product.nombreProducto}</h4>
                <span class="badge badge-primary">${product.codigo}</span>
            </div>
            
            <div class="detail-body">
                <div class="row">
                    <div class="col-md-6">
                        <div class="detail-group">
                            <label>Proveedor:</label>
                            <p>${product.proveedor?.nombre || 'Sin proveedor'}</p>
                        </div>
                        
                        <div class="detail-group">
                            <label>Precio:</label>
                            <p class="price">${product.moneda?.simboloMoneda || '₡'} ${formatNumber(product.precio)}</p>
                        </div>
                        
                        <div class="detail-group">
                            <label>Impuestos:</label>
                            <p>${product.moneda?.simboloMoneda || '₡'} ${formatNumber(product.impuestoTotal)}</p>
                        </div>
                        
                        <div class="detail-group">
                            <label>Precio Total:</label>
                            <p class="price-total">${product.moneda?.simboloMoneda || '₡'} ${formatNumber(product.precioFinal)}</p>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="detail-group">
                            <label>Stock Actual:</label>
                            <p>${formatNumber(product.inventarioActual)} ${product.unidadDeMedida?.simbolo || ''}</p>
                        </div>
                        
                        <div class="detail-group">
                            <label>Tipo de Venta:</label>
                            <p>${product.tipoVenta === 'U' ? 'Unidad' : 'Fracción'}</p>
                        </div>
                        
                        <div class="detail-group">
                            <label>Estado:</label>
                            <p>
                                <span class="badge ${product.estadoProducto === 'A' ? 'badge-success' : 'badge-danger'}">
                                    ${product.estadoProducto === 'A' ? 'Activo' : 'Inactivo'}
                                </span>
                            </p>
                        </div>
                        
                        <div class="detail-group">
                            <label>Última Actualización:</label>
                            <p>${formatDate(product.fechaActualizacion)}</p>
                        </div>
                    </div>
                </div>
                
                ${product.descripcion ? `
                <div class="detail-group">
                    <label>Descripción:</label>
                    <p>${product.descripcion}</p>
                </div>
                ` : ''}
            </div>
        </div>
    `;

  modalContent.innerHTML = html;
  $('#modalDetalleProducto').modal('show');
}

/**
 * Confirma la eliminación de un producto
 */
function confirmarEliminarProducto(id) {
  Swal.fire({
    title: '¿Estás seguro?',
    text: 'Esta acción desactivará el producto. Podrás volver a activarlo más tarde.',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#f56565',
    cancelButtonColor: '#718096',
    confirmButtonText: 'Sí, desactivar',
    cancelButtonText: 'Cancelar'
  }).then((result) => {
    if (result.isConfirmed) {
      eliminarProducto(id);
    }
  });
}

/**
 * Elimina (desactiva) un producto
 */
function eliminarProducto(id) {
  showLoading();

  fetch(`/productos/delete/${id}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'X-CSRF-TOKEN': getCsrfToken()
    }
  })
  .then(response => response.json())
  .then(data => {
    hideLoading();
    if (data.success) {
      Swal.fire({
        icon: 'success',
        title: 'Producto desactivado',
        text: 'El producto ha sido desactivado correctamente',
        timer: 2000,
        showConfirmButton: false
      }).then(() => {
        location.reload();
      });
    } else {
      showError(data.message || 'Error al desactivar el producto');
    }
  })
  .catch(error => {
    hideLoading();
    showError('Error al procesar la solicitud');
    console.error('Error:', error);
  });
}

/**
 * Activa un producto
 */
function activarProducto(id) {
  showLoading();

  fetch(`/productos/activate/${id}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'X-CSRF-TOKEN': getCsrfToken()
    }
  })
  .then(response => response.json())
  .then(data => {
    hideLoading();
    if (data.success) {
      Swal.fire({
        icon: 'success',
        title: 'Producto activado',
        text: 'El producto ha sido activado correctamente',
        timer: 2000,
        showConfirmButton: false
      }).then(() => {
        location.reload();
      });
    } else {
      showError(data.message || 'Error al activar el producto');
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
  // Aquí iría la lógica para mostrar un modal de importación
  Swal.fire({
    title: 'Importar Productos',
    html: `
            <div class="import-modal-content">
                <p>Selecciona un archivo Excel o CSV con los productos a importar:</p>
                <input type="file" id="importFile" class="swal2-input" accept=".xlsx,.xls,.csv">
                <div class="mt-3">
                    <a href="/productos/plantilla-importacion" class="btn btn-sm btn-info">
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
 * Realiza la importación de productos
 */
function performImport(file) {
  const formData = new FormData();
  formData.append('file', file);

  showLoading('Importando productos...');

  fetch('/productos/import', {
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
        html: `Se importaron ${data.imported} productos correctamente`,
        confirmButtonText: 'Aceptar'
      }).then(() => {
        location.reload();
      });
    } else {
      showError(data.message || 'Error al importar productos');
    }
  })
  .catch(error => {
    hideLoading();
    showError('Error al procesar la importación');
    console.error('Error:', error);
  });
}

/**
 * Exporta los productos
 */
function exportProducts() {
  const format = 'xlsx'; // Puedes hacer esto dinámico
  window.location.href = `/productos/export?format=${format}`;
}

/**
 * Actualiza la lista de productos
 */
function refreshProductList() {
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
 * Configura la paginación
 */
function setupPagination() {
  // La paginación ya viene del servidor con Thymeleaf
  // Aquí puedes agregar funcionalidades adicionales si es necesario
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
 * Formatea una cantidad como moneda
 */
function formatCurrency(amount) {
  // Esta función se mantiene por compatibilidad pero usa formatNumber internamente
  return formatNumber(amount);
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
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  }).format(date);
}

// Exportar funciones para uso global
window.verDetalleProducto = verDetalleProducto;
window.confirmarEliminarProducto = confirmarEliminarProducto;
window.activarProducto = activarProducto;