import {FC, useEffect} from "react";
import {showChangePassword} from "../../api/reset-password";
import {useNavigate, useSearchParams} from "react-router-dom";
import CssBaseline from "@mui/material/CssBaseline";
import Avatar from "@mui/material/Avatar";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import {Box, CircularProgress, Container} from "@mui/material";

const ChangePasswordLoading: FC = () => {

    const navigate = useNavigate();
    const [searchParams] = useSearchParams();

    const token: string = searchParams.get("token") ?? '';

    useEffect(() => {
      showChangePassword(token).then(response => {
        navigate(`/change-password/${response.data}?token=${token}`)
      })
    });

    return (
        <Container component="main" sx={{
            border: '1px solid #000000',
            boxShadow: '6px 6px 0px #00bfff',
            background: 'white'
        }} maxWidth="xs">
            <CssBaseline/>
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Avatar sx={{m: 1, bgcolor: 'primary.main'}}>
                    <LockOutlinedIcon/>
                </Avatar>
                <CircularProgress size='8rem'/>
            </Box>
        </Container>
    );
}

export default ChangePasswordLoading;
