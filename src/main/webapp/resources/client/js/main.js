(function ($) {
  'use strict';

  // Spinner
  var spinner = function () {
    setTimeout(function () {
      if ($('#spinner').length > 0) {
        $('#spinner').removeClass('show');
      }
    }, 1);
  };
  spinner(0);

  // Fixed Navbar
  $(window).scroll(function () {
    if ($(window).width() < 992) {
      if ($(this).scrollTop() > 0) {
        $('.fixed-top').addClass('shadow');
      } else {
        $('.fixed-top').removeClass('shadow');
      }
    } else {
      if ($(this).scrollTop() > 55) {
        $('.fixed-top').addClass('shadow').css('top', 0);
      } else {
        $('.fixed-top').removeClass('shadow').css('top', 0);
      }
    }
  });

  // Back to top button
  $(window).scroll(function () {
    if ($(this).scrollTop() > 300) {
      $('.back-to-top').fadeIn('slow');
    } else {
      $('.back-to-top').fadeOut('slow');
    }
  });
  $('.back-to-top').click(function () {
    $('html, body').animate({ scrollTop: 0 }, 1500, 'easeInOutExpo');
    return false;
  });

  // Testimonial carousel
  $('.testimonial-carousel').owlCarousel({
    autoplay: true,
    smartSpeed: 2000,
    center: false,
    dots: true,
    loop: true,
    margin: 25,
    nav: true,
    navText: ['<i class="bi bi-arrow-left"></i>', '<i class="bi bi-arrow-right"></i>'],
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
      },
      576: {
        items: 1,
      },
      768: {
        items: 1,
      },
      992: {
        items: 2,
      },
      1200: {
        items: 2,
      },
    },
  });

  // vegetable carousel
  $('.vegetable-carousel').owlCarousel({
    autoplay: true,
    smartSpeed: 1500,
    center: false,
    dots: true,
    loop: true,
    margin: 25,
    nav: true,
    navText: ['<i class="bi bi-arrow-left"></i>', '<i class="bi bi-arrow-right"></i>'],
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
      },
      576: {
        items: 1,
      },
      768: {
        items: 2,
      },
      992: {
        items: 3,
      },
      1200: {
        items: 4,
      },
    },
  });

  // Modal Video
  $(document).ready(function () {
    var $videoSrc;
    $('.btn-play').click(function () {
      $videoSrc = $(this).data('src');
    });
    console.log($videoSrc);

    $('#videoModal').on('shown.bs.modal', function (e) {
      $('#video').attr('src', $videoSrc + '?autoplay=1&amp;modestbranding=1&amp;showinfo=0');
    });

    $('#videoModal').on('hide.bs.modal', function (e) {
      $('#video').attr('src', $videoSrc);
    });
  });

  // Product Quantity
  //   $('.quantity button').on('click', function () {
  //     var button = $(this);
  //     var oldValue = button.parent().parent().find('input').val();
  //     if (button.hasClass('btn-plus')) {
  //       var newVal = parseFloat(oldValue) + 1;
  //     } else {
  //       if (oldValue > 0) {
  //         var newVal = parseFloat(oldValue) - 1;
  //       } else {
  //         newVal = 0;
  //       }
  //     }
  //     button.parent().parent().find('input').val(newVal);
  //   });
  // });

  $('.quantity button').on('click', function () {
    let change = 0;

    var button = $(this);
    var oldValue = button.parent().parent().find('input').val();

    if (button.hasClass('btn-plus')) {
      var newVal = parseFloat(oldValue) + 1;
      change = 1;
    } else {
      if (oldValue > 1) {
        var newVal = parseFloat(oldValue) - 1;
        change = -1;
      } else {
        newVal = 1;
      }
    }

    const input = button.parent().parent().find('input');
    input.val(newVal);

    //set form index
    const index = input.attr('data-cart-detail-index');
    const el = document.getElementById(`cartDetails${index}.quantity`);
    $(el).val(newVal);

    // get price
    const price = input.attr('data-cart-detail-price');
    const id = input.attr('data-cart-detail-id');

    const priceElement = $(`p[data-cart-detail-id='${id}']`);
    if (priceElement) {
      const newPrice = +price * newVal;
      priceElement.text(formatCurrency(newPrice.toFixed(2)) + ' đ');
    }

    // update total cart price
    const totalPriceElement = $('p[data-cart-total-price]');

    if (totalPriceElement && totalPriceElement.length) {
      const currentTotal = totalPriceElement.first().attr('data-cart-total-price');

      let newTotal = +currentTotal;

      if (change === 0) {
        newTotal = +currentTotal;
      } else {
        newTotal = change * +price + +currentTotal;
      }

      // reset change
      change = 0;

      // update
      totalPriceElement?.each(function (index, element) {
        // update text
        $(totalPriceElement[index]).text(formatCurrency(newTotal.toFixed(2)) + ' đ');

        // update data-attribute
        $(totalPriceElement[index]).attr('data-cart-total-price', newTotal);
      });
    }
  });

  function formatCurrency(value) {
    const formatter = new Intl.NumberFormat('vi-VN', {
      style: 'decimal',
      minimumFractionDigits: 0,
    });

    let formatted = formatter.format(value);
    formatted = formatted.replace(/\./g, ',');

    return formatted;
  }

  //handle filter products
  $('#btnFilter').on('click', function (event) {
    event.preventDefault();

    let factory = [];
    let targetArr = [];
    let priceArr = [];

    $('#factoryFilter .form-check-input:checked').each(function () {
      factory.push($(this).val());
    });

    $('#targetFilter .form-check-input:checked').each(function () {
      targetArr.push($(this).val());
    });

    $('#priceFilter .form-check-input:checked').each(function () {
      priceArr.push($(this).val());
    });

    let sortValue = $('input[name="radio-sort"]:checked').val();

    const currentUrl = new URL(window.location.href);
    const searchParams = currentUrl.searchParams;

    searchParams.set('page', '1');

    if (sortValue) {
      searchParams.set('sort', sortValue);
    }

    if (factory.length > 0) {
      searchParams.set('factory', factory.join(','));
    } else {
      searchParams.delete('factory');
    }

    if (targetArr.length > 0) {
      searchParams.set('target', targetArr.join(','));
    } else {
      searchParams.delete('target');
    }

    if (priceArr.length > 0) {
      searchParams.set('price', priceArr.join(','));
    } else {
      searchParams.delete('price');
    }

    window.location.href = currentUrl.toString();
  });
  //hadle auto checkbox after page loading
  //Parse the URL parameters
  const params = new URLSearchParams(window.location.search);

  //set chẹckboxes for 'factory'
  if (params.has('factory')) {
    const factoryValues = params.get('factory').split(',');
    factoryValues.forEach(function (value) {
      $(`#factoryFilter .form-check-input[value="${value}"]`).prop('checked', true);
    });
  }

  //set checkboxes for 'target'
  if (params.has('target')) {
    const targetValues = params.get('target').split(',');
    targetValues.forEach(function (value) {
      $(`#targetFilter .form-check-input[value="${value}"]`).prop('checked', true);
    });
  }

  //set checkboxes for 'price'
  if (params.has('price')) {
    const priceValues = params.get('price').split(',');
    priceValues.forEach(function (value) {
      $(`#priceFilter .form-check-input[value="${value}"]`).prop('checked', true);
    });
  }
})(jQuery);
