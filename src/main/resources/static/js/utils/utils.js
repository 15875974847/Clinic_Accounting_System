// returns today's date in yyyy-MM-dd format
function returnCurrentDateIn_yyyyMMdd_Format(){
    return todayDate = new Date().toISOString().slice(0,10);
}
