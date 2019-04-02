<template>
  <div :class="{fullscreen:fullscreen}" class="tinymce-container editor-container">
    <textarea :id="tinymceId" class="tinymce-textarea"/>
    <div class="editor-custom-btn-container">
      <imageUploader color="#1890ff" class="editor-upload-btn" @successCBK="imageSuccessCBK"/>
    </div>
  </div>
</template>

<script>
  import imageUploader from './components/imageUploader'
  import plugins from './plugins'
  import toolbar from './toolbar'

  export default {
    name: 'vue-tinymce',
    components: { imageUploader },
    props: {
      id: {
        type: String,
        default: function() {
          return 'vue-tinymce-' + +new Date() + ((Math.random() * 1000).toFixed(0) + '')
        }
      },
      value: {
        type: String,
        default: ''
      },
      toolbar: {
        type: Array,
        default(){
          return toolbar
        }
      },
      plugins: {
        type: Array,
        default(){
          return plugins
        }
      },
      menubar: {
        type: String,
        default: 'file edit insert view format table'
      },
      height: {
        type: Number,
        required: false,
        default: 360
      }
    },
    data() {
      return {
        tinymceId: this.id,
        hasInit: false,
        hasChange: false,
        fullscreen: false
      }
    },
    watch: {
      value(val) {
        if (!this.hasChange && this.hasInit) {window.tinymce.get(this.tinymceId).setContent(val)}
      }
    },
    mounted() {
      this.initTinymce()
    },
    destroyed() {
      this.destroyTinymce()
    },
    methods: {
      initTinymce() {
        const _this = this
        tinymce.init({
          language: "zh_CN",
          selector: `#${this.tinymceId}`,
          height: this.height,
          toolbar: this.toolbar,
          menubar: this.menubar,
          plugins: this.plugins,
          convert_urls: false, //这个参数加上去就可以了
          images_upload_url:'/system/file/single-upload',
          images_upload_handler: (blobInfo, success, failure) => {
            let fd = new FormData()
            fd.append('file',blobInfo.blob())
            this.request.post("/system/file/single-upload",fd).then(res => {
              success("/api/system/file/showimage/"+res.fileId);
            })
            .catch(() => {
              failure('上传失败')
            })
          },
          init_instance_callback: editor => {
            if (_this.value) {
              editor.setContent(_this.value)
            }
            _this.hasInit = true
            editor.on('NodeChange Change KeyUp SetContent', () => {
              this.hasChange = true
              this.$emit('contentChange', editor.getContent({format: 'text'}).ReplaceAll(" ","").ReplaceAll("\r","").ReplaceAll("\n",""))
              this.$emit('input', editor.getContent())
            })
          },
          setup(editor) {
            editor.on('FullscreenStateChanged', (e) => {
              _this.fullscreen = e.state
            })
          }
        })
      },
      destroyTinymce() {
        const tinymce = window.tinymce.get(this.tinymceId)
        if (this.fullscreen) {
          tinymce.execCommand('mceFullScreen')
        }
        if (tinymce) {
          tinymce.destroy()
        }
      },
      imageSuccessCBK(arr) {
        const _this = this
        arr.forEach(v => {
          window.tinymce.get(_this.tinymceId).insertContent(`<img class="wscnph" src="${v.url}" >`)
        })
      }
    }
  }
</script>

<style scoped>
  .tinymce-container {
    position: relative;
    line-height: normal;
  }
  .tinymce-container>>>.mce-fullscreen {
    z-index: 10000;
  }
  .tinymce-textarea {
    visibility: hidden;
    z-index: -1;
  }
  .editor-custom-btn-container {
    position: absolute;
    right: 4px;
    top: 4px;
    /*z-index: 2005;*/
  }
  .fullscreen .editor-custom-btn-container {
    z-index: 10000;
    position: fixed;
  }
  .editor-upload-btn {
    display: inline-block;
  }
</style>
