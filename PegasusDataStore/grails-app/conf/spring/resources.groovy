import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info

beans = {
    customOpenAPI(OpenAPI) {
        info = new Info(
                title: 'PegasusBet API',
                version: '1.0.0',
                description: 'API para gerenciar dados do PegasusBet',
                contact: new Contact(
                        name: 'Suporte PegasusBet',
                        email: 'suporte@pegasusbet.com'
                )
        )
    }

}
