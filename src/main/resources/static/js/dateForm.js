function formatDate(input) {
    let date = new Date(input.value);
    if (!isNaN(date.getTime())) {
        let day = ('0' + date.getDate()).slice(-2);
        let month = ('0' + (date.getMonth() + 1)).slice(-2);
        let year = date.getFullYear();
        input.value = year + '-' + month + '-' + day;
    } else {
        input.type = 'text';
    }
}

document.getElementById("dateForm").addEventListener("submit", function(event) {
    var checkinDate = document.getElementById("checkin_date").value;
    var checkoutDate = document.getElementById("checkout_date").value;

    if(checkinDate) {
        document.getElementById("checkin_date").value = new Date(checkinDate).toISOString().split('T')[0];
    }
    if(checkoutDate) {
        document.getElementById("checkout_date").value = new Date(checkoutDate).toISOString().split('T')[0];
    }
});
