import org.codehaus.groovy.grails.commons.ConfigurationHolder;


beans = {

  def config = ConfigurationHolder.config

  if (config.mail.sendMails) {
    mailSender(org.springframework.mail.javamail.JavaMailSenderImpl) {
      host = config.mail.host
    }
  }

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

  autoProxyCreator(org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator) {
    proxyTargetClass = true
  }

  tweetingAspect(com.wannalunch.aop.TweetingAspect)

  mailingAspect(com.wannalunch.aop.MailingAspect)

  mailTrigger(org.springframework.scheduling.quartz.SimpleTriggerBean) {
    jobDetail = { org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean bean ->
      targetObject = ref("mailService");
      targetMethod = "maybeSendMail"
    }
    startDelay = 0
    repeatInterval = 5 * 60 * 1000
  }

  quartzScheduler(org.springframework.scheduling.quartz.SchedulerFactoryBean) {
    triggers = [
        ref("mailTrigger")
    ]
  }


}