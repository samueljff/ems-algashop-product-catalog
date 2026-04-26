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
            name: "Desktops",
            enabled: true
        ])
    }
    response {
        status 200
        headers {
            contentType('application/json')
        }
        body([
            id: fromRequest().path(3),
            name: anyNonBlankString(),
            enabled: anyBoolean()
        ])
    }
}