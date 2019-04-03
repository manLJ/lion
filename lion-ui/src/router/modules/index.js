
import lodash from 'lodash'
const files = require.context('.', true, /\.js$/)

let configRouters = []
/**
 * inject routers
 */

files.keys().forEach(key => {
  if (key === './index.js') return;
  configRouters = configRouters.concat(files(key).default) // 读取出文件中的default模块
})

export default lodash.sortBy(configRouters, ['sortIndex']) // 抛出一个Vue-router期待的结构的数组
