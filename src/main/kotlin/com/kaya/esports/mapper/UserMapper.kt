package com.kaya.esports.mapper

import com.kaya.esports.dto.UserDTO
import com.kaya.esports.entity.User

class UserMapper {
    companion object {
        fun userDTOToUserEntity(userDTO: UserDTO): User {
            return User(userDTO.userName, userDTO.password, null, null)
        }

        fun userEntityToUserDTO(user: User): UserDTO {
            return UserDTO(user.userName, user.password)
        }
    }
}