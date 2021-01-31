$(function () {
   $('#cat_id').on('change', function () {
      var cat_id = $(this).val();
      $.get(route + '/orders/getProducts', { cat_id: cat_id }, function (data) {
         $('#product_id').html(data);
      });

      // if (cat_id) {
      //     $.ajax({
      //         type: 'GET',
      //         async: true,
      //         url: route + '/orders/getProducts',
      //         data: 'cat_id=' + cat_id,
      //         success: function (data) {
      //             $('#product_id').html(data);
      //         }
      //     });
      // }
   });
});

$(function () {
   $('#product_id').on('change', function () {
      var product_id = $(this).val();
      $.get(route + '/orders/getUnitPrice', { product_id: product_id }, function (data) {
         $('#unit_price').val(data);
      });
   });
});

$(function () {
   $('#qty').on('change', function () {
      var price = $('#unit_price').val();
      var qty = $(this).val();
      total = price * qty;
      $('#total').val(total);
   });
});