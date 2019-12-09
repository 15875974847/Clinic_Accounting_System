// returns today's date in yyyy-MM-dd format
function returnCurrentDateIn_yyyyMMdd_Format(){
    return new Date().toISOString().slice(0,10);
}

function returnCurrentDate(){
    return new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
}
