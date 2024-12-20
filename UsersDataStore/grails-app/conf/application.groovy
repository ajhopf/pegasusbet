grails.plugin.springsecurity.rest.login.endpointUrl= '/login'

grails.plugin.springsecurity.rest.token.storage.jwt.secret= 'uma_chave_muito_secreta_e_bem_longa_para_segurança'
grails.plugin.springsecurity.userLookup.userDomainClassName = 'users.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'users.UserRole'
grails.plugin.springsecurity.authority.className = 'users.Role'


grails.plugin.springsecurity.filterChain.chainMap = [
	[
			pattern: '/**',
	 		filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
	]
]

