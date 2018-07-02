function timeChange(id){
	var str = $('#'+id).val().split('-')
	return str[0]+'/'+str[1]+'/'+str[2]
}