/**
 * @Author: jianglei
 * @Date:   2017-10-12 12:06:49
 */
export default function (list ,key) {
  var tree = list.filter(function(parent) {
    var branchArr = list.filter(function(child) {
      return parent.id == child[key];
    });
    parent.children = [];
    if (branchArr.length > 0) {
      parent.children = branchArr;
    }
    return (parent[key] == null||parent[key] == "");
  });
  return tree;
}
