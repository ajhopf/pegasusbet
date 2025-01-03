grails.plugin.springsecurity.rest.login.endpointUrl= '/api/login'

grails.plugin.springsecurity.rest.token.storage.jwt.secret= 'uma_chave_muito_secreta_e_bem_longa_para_segurança'
grails.plugin.springsecurity.userLookup.userDomainClassName = 'users.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'users.UserRole'
grails.plugin.springsecurity.authority.className = 'users.Role'
grails.plugin.springsecurity.rest.token.storage.jwt.expiration = 86400

grails.plugin.springsecurity.interceptUrlMap = [
//		[pattern: '/user', methods: ['POST'], access: ['permitAll']],
//		[pattern: '/user/admin', methods: ['POST'], access: ['permitAll']],
		[pattern: '/api/login',      access: ['permitAll']],
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

		[pattern: '/login/**',       access: ['permitAll']],
		[pattern: '/logout',         access: ['permitAll']],
		[pattern: '/logout/**',      access: ['permitAll']],

]


grails.plugin.springsecurity.filterChain.chainMap = [
		[pattern: '/user', filters: 'JOINED_FILTERS,-authenticationProcessingFilter'],
//		[pattern: '/user/admin', filters: 'JOINED_FILTERS,-authenticationProcessingFilter'],
		[pattern: '/api/login', filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter'],
		[pattern: '/**', filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
	]
]

