package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.usecase.port.MemberPersistencePort
import org.springframework.stereotype.Service

@Service
class UserSecurityService(
    private val memberPersistencePort: MemberPersistencePort
) : UserSecurity {
    /**
     * Handle exception in security if member is null
     */
    override fun findMemberByEmail(email: String): Member? = memberPersistencePort.findMemberByEmail(email)
}
