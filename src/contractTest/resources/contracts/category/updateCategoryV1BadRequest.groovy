package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method PUT()
        urlPath("/api/v1/categories/a3c91d7f-52be-4e8a-b017-9f2e847d1c34")
        headers {
            contentType('application/json')
        }
        body([
            name   : " ",
            enabled: true
        ])
    }
    response {
        status 400
        headers {
            contentType('application/problem+json')
        }
        body([
            instance: fromRequest().path(),
            type    : "/errors/invalid-fields",
            title   : "Invalid fields",
            detail  : "One or more fields are invalid",
            fields  : [
                name: anyNonBlankString()
            ]
        ])
    }
}