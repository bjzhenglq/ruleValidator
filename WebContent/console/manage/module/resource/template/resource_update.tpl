<div class="ui-form-panel">
	<form id="form-resource-update">
		<div class="ui-form-field hidden">
			<label class="label">模块ID</label>
			<input class="textfield readonly" value="{{moduleId}}"
				readonly="readonly">
			<input name="moduleId" type="hidden" value="{{moduleId}}">
		</div>
		<div class="ui-form-field hidden">
			<label class="label">资源ID</label>
			<input class="textfield readonly" value="{{resourceId}}"
				readonly="readonly">
			<input name="resourceId" type="hidden" value="{{resourceId}}">
		</div>
		<div class="ui-form-field">
			<label class="label">资源编码</label>
			<input name="resourceCode" class="textfield" placeholder="请填写资源编码"
				value="{{resourceCode}}" maxlength="128">
		</div>
		<div class="ui-form-field">
			<label class="label">资源名称</label>
			<input name="resourceName" class="textfield" placeholder="请填写资源名称"
				value="{{resourceName}}">
		</div>
		<div class="ui-form-field" style="width: 460px;">
			<label class="label">URL</label>
			<!-- <input name="url" class="textfield" value="{{#raw url}}{{/raw}}" title="{{#raw url}}{{/raw}}" placeholder="请填写URL"
				style="width: 190px;"> -->
			<input name="url" class="textfield" value="{{url}}" title="{{url}}" placeholder="请填写URL"
				style="width: 190px;">
		</div>
		<div class="ui-form-field">
			<label class="label">安全级别</label>
			{{#select collection_securityLevel}} {{/select}}
		</div>
	</form>
</div>