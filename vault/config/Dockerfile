FROM hashicorp/vault:1.13.3

RUN mkdir -p /vault/config && \
    chown -R vault:vault /vault/config

COPY --chown=vault:vault vault.hcl /vault/config/vault.hcl

RUN chmod 644 /vault/config/vault.hcl

EXPOSE 8200

USER vault

ENTRYPOINT ["vault", "server", "-config=/vault/config/vault.hcl"]