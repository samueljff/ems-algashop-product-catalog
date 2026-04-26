package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method PUT()
        urlPath("/api/v1/categories/00000000-0000-0000-0000-000000000000")
        headers {
            contentType('application/json')
        }
        body([
            name   : "Desktops",
            enabled: true
        ])
    }
    response {
        status 404
        headers {
            contentType('application/problem+json')
        }
        body([
            instance: fromRequest().path(),
            type    : "/errors/not-found",
            title   : "Not found",
        ])
    }
}