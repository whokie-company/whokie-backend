package supernova.whokie.user.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import supernova.whokie.user.Gender;
import supernova.whokie.user.Role;
import supernova.whokie.user.service.dto.UserModel;

public class UserResponse {


    @Builder
    public record PickedInfo(
        Long userId,
        String name,
        String imageUrl
    ) {

        public static UserResponse.PickedInfo from(UserModel.PickedInfo pickedInfo) {
            return PickedInfo.builder()
                .userId(pickedInfo.userId())
                .name(pickedInfo.name())
                .imageUrl(pickedInfo.imageUrl())
                .build();
        }

    }

    @Builder
    public record Info(
        Long id,
        String name,
        String imageUrl,
        String email,
        Gender gender,
        int point,
        int age,
        Role role,
        Long kakaoId,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
    ) {

        public static Info from(UserModel.Info user) {
            return Info.builder()
                .id(user.id())
                .name(user.name())
                .imageUrl(user.imageUrl())
                .email(user.email())
                .gender(user.gender())
                .point(user.point())
                .age(user.age())
                .role(user.role())
                .kakaoId(user.kakaoId())
                .createdAt(user.createdAt())
                .modifiedAt(user.modifiedAt())
                .build();
        }
    }

    @Builder
    public record Point(
        int amount
    ) {

        public static Point from(UserModel.Point user) {
            return Point.builder()
                .amount(user.amount())
                .build();
        }
    }

    @Builder
    public record Login(
        Long userId,
        String jwt,
        Role role
    ) {

        public static Login from(UserModel.Login model) {
            return Login.builder()
                .userId(model.userId())
                .jwt(model.jwt())
                .role(model.role())
                .build();
        }
    }

    @Builder
    public record PersonalInfo(
        String name,
        Gender gender,
        LocalDate birthDate
    ) {

        public static PersonalInfo from(UserModel.Info model) {
            return PersonalInfo.builder()
                .name(model.name())
                .gender(model.gender())
                .birthDate(model.birthDate())
                .build();
        }
    }
}
