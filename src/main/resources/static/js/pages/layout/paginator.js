/**
 * Paginador Mejorado - SAMYX
 * Mejoras visuales y de experiencia de usuario
 */

(function() {
  'use strict';

  // Inicializar cuando el DOM esté listo
  document.addEventListener('DOMContentLoaded', function() {
    initializePaginators();
  });

  function initializePaginators() {
    const paginators = document.querySelectorAll('.paginator-nav');

    paginators.forEach(paginator => {
      // Añadir efecto de carga al hacer clic en enlaces
      const links = paginator.querySelectorAll('.page-link:not(.page-item.disabled .page-link)');

      links.forEach(link => {
        if (link.tagName === 'A') {
          link.addEventListener('click', function(e) {
            // Mostrar estado de carga
            paginator.classList.add('loading');

            // Efecto visual en el botón clickeado
            this.style.transform = 'scale(0.95)';
            setTimeout(() => {
              this.style.transform = '';
            }, 200);
          });
        }
      });

      // Añadir tooltips
      addTooltips(paginator);

      // Navegación con teclado
      enableKeyboardNavigation(paginator);
    });
  }

  function addTooltips(paginator) {
    const tooltips = {
      '.page-link .fa-angle-double-left': 'Primera página',
      '.page-link .fa-angle-left': 'Página anterior',
      '.page-link .fa-angle-right': 'Página siguiente',
      '.page-link .fa-angle-double-right': 'Última página'
    };

    Object.entries(tooltips).forEach(([selector, text]) => {
      const elements = paginator.querySelectorAll(selector);
      elements.forEach(el => {
        const link = el.closest('.page-link');
        if (link) {
          link.setAttribute('title', text);
        }
      });
    });
  }

  function enableKeyboardNavigation(paginator) {
    document.addEventListener('keydown', function(e) {
      // Solo si no hay inputs activos
      if (document.activeElement.tagName === 'INPUT' ||
          document.activeElement.tagName === 'TEXTAREA') {
        return;
      }

      if (e.key === 'ArrowLeft') {
        const prevLink = paginator.querySelector('.page-item:not(.disabled) .page-link .fa-angle-left');
        if (prevLink) {
          prevLink.closest('.page-link').click();
        }
      } else if (e.key === 'ArrowRight') {
        const nextLink = paginator.querySelector('.page-item:not(.disabled) .page-link .fa-angle-right');
        if (nextLink) {
          nextLink.closest('.page-link').click();
        }
      }
    });
  }

  // Si se usa AJAX, re-inicializar
  if (window.jQuery) {
    $(document).ajaxComplete(function() {
      setTimeout(initializePaginators, 100);
    });
  }

})();