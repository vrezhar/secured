
// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.ttreport.auth.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.ttreport.auth.UserRole'
grails.plugin.springsecurity.authority.className = 'com.com.ttreport.auth.Role'
grails.plugin.springsecurity.logout.postOnly = false
//grails.plugin.springsecurity.adh.errorPage = null
grails.plugin.springsecurity.successHandler.ajaxSuccessUrl = "/home"
grails.plugin.springsecurity.successHandler.defaultTargetUrl = "/home"
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
		[pattern: '/',               access: ['permitAll']],
		[pattern: '/error',          access: ['permitAll']],
		[pattern: '/index',          access: ['permitAll']],
		[pattern: '/index.gsp',      access: ['permitAll']],
		[pattern: '/shutdown',       access: ['permitAll']],
		[pattern: '/assets/**',      access: ['permitAll']],
		[pattern: '/**/js/**',       access: ['permitAll']],
		[pattern: '/**/css/**',      access: ['permitAll']],
		[pattern: '/**/images/**',   access: ['permitAll']],
		[pattern: '/**/favicon.ico', access: ['permitAll']],
		[pattern: '/user/register', access:['permitAll']],
		[pattern: '/register', access:['permitAll']]
//		,[pattern: '/assets/stylesheets/bootstrap-reboot.css', access:['permitAll']],
//		[pattern: '/assets/stylesheets/bootstrap-reboot.css', access:['permitAll']],
//		[pattern: '/assets/stylesheets/bootstrap-grid.css', access:['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
		[pattern: '/assets/**',      filters: 'none'],
		[pattern: '/**/js/**',       filters: 'none'],
		[pattern: '/**/css/**',      filters: 'none'],
		[pattern: '/**/images/**',   filters: 'none'],
		[pattern: '/**/favicon.ico', filters: 'none'],
		[pattern: '/**',             filters: 'JOINED_FILTERS']
]

