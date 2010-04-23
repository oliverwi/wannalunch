beans = {

  userMessageSource(com.wannalunch.support.UserLocaleMessageSourceAccessor) {
    messageSource = ref('messageSource')
  }

  customPropertyRegistrar(com.wannalunch.propertyeditor.CustomPropertyEditorRegistrar) {
    dateEditor = ref('dateEditorProxy')
    timeEditor = ref('timeEditorProxy')
  }

  dateEditor(com.wannalunch.propertyeditor.LocalDateEditor, ref('userMessageSource')) { editor ->
    editor.scope = 'request'
  }

  dateEditorProxy(org.springframework.aop.scope.ScopedProxyFactoryBean) {
    targetBeanName = 'dateEditor'
    proxyTargetClass = true
  }

  timeEditor(com.wannalunch.propertyeditor.LocalTimeEditor, ref('userMessageSource')) { editor ->
    editor.scope = 'request'
  }

  timeEditorProxy(org.springframework.aop.scope.ScopedProxyFactoryBean) {
    targetBeanName = 'timeEditor'
    proxyTargetClass = true
  }

}