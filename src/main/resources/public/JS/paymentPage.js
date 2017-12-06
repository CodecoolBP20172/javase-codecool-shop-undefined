// var FormStuff = {
//
//     init: function() {
//         this.applyConditionalRequired();
//         this.bindUIActions();
//     },
//
//     bindUIActions: function() {
//         $("input[type='radio'], input[type='checkbox']").on("change", this.applyConditionalRequired);
//     },
//
//     applyConditionalRequired: function() {
//
//         $(".require-if-active").each(function() {
//             var el = $(this);
//             if ($(el.data("require-pair")).is(":checked")) {
//                 el.prop("required", true);
//             } else {
//                 el.prop("required", false);
//             }
//         });
//
//     }
//
// };

function addRadioEventListeners() {
    $('#credit_card_input').hide();
    $('#paypal_input').hide();

    $("input[type='radio']").on("change", function () {
        if (this.value == 'Credit Card') {
            $('#credit_card_input').show();
            $('#paypal_input').hide();
        } else if (this.value == 'Paypal') {
            $('#credit_card_input').hide();
            $('#paypal_input').show();
        }
    });
}

window.addEventListener('load', addRadioEventListeners);
