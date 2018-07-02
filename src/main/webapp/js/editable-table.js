document.write('<script src="../../js/editable-paging.js"></script>'); 
var EditableTable = function () {
	'use strict';

    return {
        init: function () {
            var oTable = getTable();
            jQuery('#editable-sample_wrapper .dataTables_filter input').addClass("form-control medium");
            jQuery('#editable-sample_wrapper .dataTables_length select').addClass("form-control xsmall");
        }}
}();//function