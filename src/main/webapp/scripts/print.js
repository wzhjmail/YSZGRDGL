// strPrintName 打印任务名
// printDatagrid 要打印的datagrid
//还要针对是否需要打印序号的问题
function CreateFormPage(strPrintName, printDatagrid) {
    var tableString = '';
    var frozenColumns = printDatagrid.datagrid("options").frozenColumns;  // 得到frozenColumns对象
    var columns = printDatagrid.datagrid("options").columns;    // 得到columns对象
    var nameList = '';
    //记录Column的个数
    var length=columns.length;
    //需要的总长度
    var longd=800;
    //columnLength
    var columnLen=0;
    // 载入title
    var  rowh=1;
    if (typeof columns != 'undefined' && columns != '') {
    	//tableString += '\n<tr><th width="60px;">序号</th>';
        $(columns).each(function (index) {
        	//此处需要计算表头
            columnLen=longd/length;
            //添加表头信息
            if (typeof frozenColumns != 'undefined' && typeof frozenColumns[index] != 'undefined') {
                for (var i = 0; i < frozenColumns[index].length; ++i) {
                    if (!frozenColumns[index][i].hidden) {
                    	//frozenColumns[index][i].width 
                        tableString += '\n<th width="' + columnLen+ '"';
                        if (typeof frozenColumns[index][i].rowspan != 'undefined' && frozenColumns[index][i].rowspan > 1) {
                            if(frozenColumns[index][i].rowspan>rowh){
                            	rowh=frozenColumns[index][i].rowspan
                            }
                        	tableString += ' rowspan="' + frozenColumns[index][i].rowspan + '"';
                        }
                        if (typeof frozenColumns[index][i].colspan != 'undefined' && frozenColumns[index][i].colspan > 1) {
                            tableString += ' colspan="' + frozenColumns[index][i].colspan + '"';
                        }
                        if (typeof frozenColumns[index][i].field != 'undefined' && frozenColumns[index][i].field != '') {
                            nameList += ',{"f":"' + frozenColumns[index][i].field + '", "a":"' + frozenColumns[index][i].align + '"}';
                        }
                        tableString += '>' + frozenColumns[0][i].title + '</th>';
                    }
                }
            }
           
            for (var i = 0; i < columns[index].length; ++i) {
                if (!columns[index][i].hidden) {
                	//columns[index][i].width
                    tableString += '\n<th width="' + columnLen + '"';
                    if (typeof columns[index][i].rowspan != 'undefined' && columns[index][i].rowspan > 1) {
                    	 if(columns[index][i].rowspan>rowh){
                         	rowh=columns[index][i].rowspan
                         }
                    	tableString += ' rowspan="' + columns[index][i].rowspan + '"';
                    }
                    if (typeof columns[index][i].colspan != 'undefined' && columns[index][i].colspan > 1) {
                        tableString += ' colspan="' + columns[index][i].colspan + '"';
                    }
                    if (typeof columns[index][i].field != 'undefined' && columns[index][i].field != '') {
                        nameList += ',{"f":"' + columns[index][i].field + '", "a":"' + columns[index][i].align + '"}';
                    }
                    tableString += '>' + columns[index][i].title + '</th>';
                }
            }
            tableString += '\n</tr>';
        });

        tableString = '<table cellspacing="0" class="pb">\n<tr><th width="60px;" rowspan='+rowh+'>序号</th>'+tableString+'\n</tr>';
    }
    // 载入内容
    var rows = printDatagrid.datagrid("getRows"); // 这段代码是获取当前页的所有行
    var nl = eval('([' + nameList.substring(1) + '])');
    // alert(nl);
    //由于field的
    var clen=printDatagrid.datagrid("getColumnFields").length
    for (var i = 0; i < rows.length; ++i) {
        tableString += '\n<tr>';
//        $(nl).each(function (j) {
//            var e = nl[j].f.lastIndexOf('_0');
//            
//            tableString += '\n<td';
//            if (nl[j].a != 'undefined' && nl[j].a != '') {
//                tableString += ' style="text-align:' + nl[j].a + ';"';
//            }
//            tableString += '>';
//            if (e + 2 == nl[j].f.length) {
//                tableString += rows[i][nl[j].f.substring(0, e)];
//            }
//            else
//                tableString += rows[i][nl[j].f];
//            tableString += '</td>';
//        });
          for(var j=0;j<clen+1;j++) {
        	  if(j==0){
        		  tableString+="\n<td style='text-align:center;height:23px;'>"+(i+1).toString()+"</td>";
        	  }
        	  else{
        		  tableString+="\n<td style='text-align:center;height:23px;'>"+rows[i][j]+"</td>";
        	  }
        	 
          }
        tableString += '\n</tr>';
    }
    tableString += '\n</table>'; 
    window.showModalDialog("print.htm", tableString,
    "location:No;status:No;help:No;dialogWidth:900px;dialogHeight:600px;scroll:auto;");
}