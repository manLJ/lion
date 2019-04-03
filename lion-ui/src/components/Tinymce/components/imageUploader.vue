<template>
  <div class="upload-container">
    <el-button :style="{background:color,borderColor:color}" icon="el-icon-upload" size="mini" type="primary" @click=" dialogVisible=true">批量上传图片
    </el-button>
    <el-dialog :visible.sync="dialogVisible">
      <el-upload
        :headers = "headers"
        :multiple="true"
        :show-file-list="true"
        :on-remove="handleRemove"
        :on-success="handleSuccess"
        :on-error="handleError"
        :before-upload="beforeUpload"
        class="editor-slide-upload"
        action="/api/system/file/single-upload"
        list-type="picture-card">
        <el-button size="small" type="primary">点击上传</el-button>
      </el-upload>
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="handleSubmit">确 定</el-button>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'EditorSlideUpload',
  props: {
    color: {
      type: String,
      default: '#1890ff'
    }
  },
  data() {
    return {
      dialogVisible: false,
      fileUploadList: {},
      uploadSuccessList: [],
      headers: {
        //token为系统访问需要的,放在header中
        'X-Auth-Token': this.$ls.get("Admin-Token")
      }
    }
  },
  methods: {
    checkAllSuccess() {
      return Object.keys(this.fileUploadList).every(item => this.fileUploadList[item].hasSuccess)
    },
    handleSubmit() {
      console.log(this.uploadSuccessList)
      console.log(this.fileUploadList)

      if (this.uploadSuccessList.length != this.$lodash.keys(this.fileUploadList).length)
        this.$message('请等待所有图片上传成功 或 出现了网络问题，请刷新页面重新上传！')


      this.$emit('successCBK', this.uploadSuccessList)
      this.fileUploadList = {}
      this.uploadSuccessList = []
      this.dialogVisible = false
    },
    handleSuccess(response, file,fileList) {
      this.fileUploadList[file.uid].url = "/api/system/file/showimage/" + response.fileId
      this.uploadSuccessList.push(this.fileUploadList[file.uid])
    },
    handleError(error, file,fileList) {
      if(error.message) this.$message.error("文件" + file.name + "上传失败,原因：" + error.message)
      else this.$message.error("文件" + file.name + "上传失败");
    },
    handleRemove(file) {
      this.fileUploadList=this.$lodash.omit(this.fileUploadList, [file.uid]);
      this.$lodash.remove(this.uploadSuccessList, function(_file) {
        return _file.uuid == file.uuid;
      });
    },
    beforeUpload(file) {
      const _self = this
      const _URL = window.URL || window.webkitURL
      const fileId = file.uid
      this.fileUploadList[fileId] = {}
      return new Promise((resolve, reject) => {
        const img = new Image()
        img.src = _URL.createObjectURL(file)
        img.onload = function() {
          _self.fileUploadList[fileId] = { hasSuccess: false, uid: file.uid, width: this.width, height: this.height }
        }
        resolve(true)
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.editor-slide-upload {
  margin-bottom: 20px;
  /deep/ .el-upload--picture-card {
    width: 100%;
  }
}
</style>
