
function addEventListeners() {
    $('#credit_card_input').hide();
    $('#paypal_input').hide();

    $('input[type="radio"]').on('change', function () {
        if (this.value === 'Credit Card') {
            $('#credit_card_input').show();
            $('#paypal_input').hide();
            $('.number_input').on('keypress', isNumber);
        } else if (this.value === 'Paypal') {
            $('#credit_card_input').hide();
            $('#paypal_input').show();
        }
    });
}


function isNumber(evt) {
        evt = (evt) ? evt : window.event;
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                return false;
            }
        return true;
    }

window.addEventListener('load', addEventListeners);
