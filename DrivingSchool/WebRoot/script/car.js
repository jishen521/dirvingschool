Ext.onReady(function() {
	Ext.define('Car', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'id',
			type : 'long'
		}, {
			name : 'plateNum',
			type : 'string'
		}, {
			name : 'regDate',
			type : 'string'
		}, {
			name : 'remark',
			type : 'string'
		}, {
			name : 'type',
			type : 'string'
		}, {
			name : 'stuName',
			type : 'stirng'
		}, {
			name : 'coachName',
			type : 'string'
		}, {
			name : 'stuId',
			type : 'long'
		}, {
			name : 'coachId',
			type : 'long'
		} ]
	});

	var store = Ext.create('Ext.data.Store', {
		model : 'Car',
		proxy : {
			type : 'ajax',
			api : {
				read : 'getCarsAction',
				create : 'addCarAction',
				updaet : 'editCarAction',
				destory : 'deleteCarAction'
			},
			reader : {
				type : 'json'
			}
		},
		listeners : {
			update : function(store, record) {
				Ext.Ajax.request({
					url : 'editCarAction',
					params : {
						'stock.id' : record.get('id'),
						'stock.storesName' : record.get('storesName'),
						'stock.storesId' : record.get('storesId'),
						'stock.price' : record.get('price'),
						'stock.currentNum' : record.get('currentNum'),
						'stock.minNum' : record.get('minNum')
					},
					success : function(response) {
						store.reload();
					}
				});
			},
			exception : function(proxy, response, operation) {
				Ext.MessageBox.show({
					title : '服务器端异常',
					msg : operation.getError(),
					icon : Ext.MessageBox.ERROR,
					buttons : Ext.Msg.OK
				});
			},
			load : function(store, records, successful, operation, opts) {
				if (!successful) {
					Ext.Msg.alert('提示', '数据加载失败!');

				}
			},
			write : function(proxy, operation) {
				Ext.example.msg(operation.action, operation.resultSet.message);
			}
		},
		autoLoad : true
	});

	var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
		clicksToEdit : 2
	});

	var carGrid = Ext.create('Ext.grid.Panel', {
		id : 'carsList',
		layout : 'fit',
		// width : 600,
		height : '100%',
		frame : true,
		store : store,
		plugins : [ rowEditing ],
		selType : 'rowmodel',
		// style : 'margin: 50px',
		renderTo : Ext.getBody(),
		columns : [ {
			text : 'id',
			// xtype : 'hidden',
			hidden : true,
			dataIndex : 'id'
		}, {
			text : '品牌',
			sortable : true,
			dataIndex : 'type',
			editor : {
				xtype : 'textfield',
				hideTrigger : true,
				allowBlank : false
			}
		}, {
			text : '车牌号码',
			sortable : true,
			dataIndex : 'plateNum',
			editor : {
				xtype : 'textfield',
				allowBlank : false
			}
		}, {
			text : '学生',
			sortable : true,
			dataIndex : 'stuName'
		}, {
			text : '教练',
			sortable : true,
			dataIndex : 'coachName'
		}, {
			text : '注册时间',
			sortable : true,
			dataIndex : 'regDate',
			editor : {
				xtype : 'datefield',
				allowBlank : false
			}
		}, {
			text : 'stuId',
			hidden : true,
			type : 'long',
			dataIndex : 'stuId'
		}, {
			name : 'coachId',
			hidden : true,
			type : 'string',
			dataIndex : 'coachId'
		} ],
		tbar : [
				{
					text : '添加',
					handler : function() {
						addStores();
					}
				},
				'-',
				{
					text : '删除',
					handler : function() {
						var selectedModel = carGrid.getSelectionModel();
						if (selectedModel.hasSelection()) {
							var record = selectedModel.getSelection();
							Ext.Msg.confirm("<font color='red'>系统提示</font>",
									"您确定要删除选择的数据吗?", function(btn) {
										if (btn == "yes") {
											deleteStores(record);
										}
									});
						} else {
							Ext.Msg.alert('失败', '请选择一行数据进行删除');
						}
					}
				} ]
	});

	function addStores() {
		var addForm = Ext.create('Ext.form.Panel', {
			bodyPadding : 5,
			// Fields will be arranged vertically, stretched to full width
			layout : 'form',
			frame : true,
			bodyBorder : false,
			// The form will submit an AJAX request to this URL when submitted
			url : 'addStockAction',
			items : [ {
				fieldLabel : '名称',
				xtype : 'textfield',
				maxValue : 200,
				minValue : 1,
				name : 'stock.storesName',
				allowBlank : false
			}, {
				fieldLabel : '编号',
				xtype : 'textfield',
				name : 'stock.storesId',
				allowBlank : false
			}, {
				fieldLabel : '价格',
				xtype : 'numberfield',
				name : 'stock.price',
				allowBlank : false
			}, {
				fieldLabel : '当前数量',
				xtype : 'numberfield',
				name : 'stock.currentNum',
				allowBlank : false
			}, {
				fieldLabel : '最低需求',
				xtype : 'numberfield',
				name : 'stock.minNum',
				allowBlank : false
			} ],

			// Reset and Submit buttons
			buttons : [ {
				text : '提交',
				formBind : true, // only enabled once the form is valid
				disabled : true,
				handler : function() {
					var form = this.up('form').getForm();
					if (form.isValid()) {
						form.submit({
							success : function(form, action) {
								Ext.Msg.alert('成功', action.result.msg);
								store.reload();
								win.close();
							},
							failure : function(form, action) {
								Ext.Msg.alert('失败', action.result.msg);
							}
						});
					}
				}
			}, {
				text : '重置',
				handler : function() {
					this.up('form').getForm().reset();
				}
			} ]
		});

		var win = Ext.create('Ext.window.Window', {
			layout : 'fit',
			width : 400,
			title : '删除库存',
			items : [ addForm ]
		});
		win.show();
	}
	function deleteStores(records) {
		var id = records[0].get('id');
		Ext.Ajax.request({
			url : 'deleteStockAction',
			params : {
				id : id
			},
			method : 'post',
			success : function(response) {
				var json = Ext.JSON.decode(response.responseText);
				Ext.Msg.alert('成功', json.msg);
				store.reload();
			},
			failure : function(response) {
				var json = Ext.JSON.decode(response.responseText);
				Ext.Msg.alert('失败', json.msg);
			}
		});
	}
});