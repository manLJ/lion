const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  name: state => state.user.name,
  roles: state => state.user.roles,
  visitPermissions: state => state.user.visitPermissions,
  operationPermissions: state => state.user.operationPermissions
}
export default getters
