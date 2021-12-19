import {FC, useState} from "react";
import * as yup from "yup";
import {Controller, SubmitHandler, useForm} from "react-hook-form";
import {yupResolver} from "@hookform/resolvers/yup";
import {resetPassword} from "../../api/reset-password";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import Avatar from "@mui/material/Avatar";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import {Box, Container} from "@mui/material";
import Button from "@mui/material/Button";

interface IFormInputs {
  email: string;
}

const schema = yup.object().shape({
  email: yup.string().email().required("E-mail address is required"),
});

const ResetPassword: FC = () => {

  const {
    handleSubmit,
    control,
    formState: {errors},
  } = useForm<IFormInputs>({resolver: yupResolver(schema)})

  const navigate = useNavigate();


  const [buttonPressed, setButtonPressed] = useState(false);

  const formSubmitHandler: SubmitHandler<IFormInputs> = (data: IFormInputs) => {
    setButtonPressed(true);
    resetPassword(data.email);
    toast.success('E-mail was sent to you.', {
      position: "top-right",
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
    });
  }
  return (
    <>
      {!buttonPressed ?
        <Container component="main" sx={{
          border: '1px solid #000000',
          boxShadow: '6px 6px 0px #00bfff',
          backgroundColor: 'white',
          mt: 20,
          paddingTop: 8,
          paddingBottom: 2,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center'
        }} maxWidth="xs">
          <Avatar sx={{m: 1, bgcolor: 'primary.main'}}>
            <LockOutlinedIcon/>
          </Avatar>
          <Typography component="h1" variant="h4">
            Reset your password
          </Typography>
          <Box component="form" onSubmit={handleSubmit(formSubmitHandler)} noValidate
               sx={{mt: 1}}>
            <p>Enter your account's email address and we will send you a password reset link.</p>
            <Controller
              name='email'
              control={control}
              defaultValue='mail@mail.com'
              render={({field}) => (
                <TextField
                  {...field}
                  label="E-mail address"
                  variant="outlined"
                  error={!!errors.email}
                  helperText={errors.email ? errors.email?.message : ''}
                  margin="normal"
                  required
                  fullWidth
                  id="email"
                  name="email"
                  autoComplete="email"
                  autoFocus
                />
              )}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{mt: 3, mb: 2}}
            >
              Send password reset email
            </Button>
          </Box>
        </Container>
        :
        <Container component="main" sx={{
          border: '1px solid #000000',
          boxShadow: '6px 6px 0px #00bfff',
          backgroundColor: 'white',
          mt: 20,
          paddingTop: 8,
          paddingBottom: 2,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center'
        }} maxWidth="xs">
          <Avatar sx={{m: 1, bgcolor: 'primary.main'}}>
            <LockOutlinedIcon/>
          </Avatar>
          <Typography component="h1" variant="h4">
            Reset your password
          </Typography>
          <Box sx={{mt: 1}}>
            <p>Check your email for a link to reset your password. If it doesn’t appear within a few
              minutes, check your spam folder.</p>
            <Button
              fullWidth
              variant="contained"
              sx={{mt: 3, mb: 2}}
              onClick={() => navigate('/login')}
            >
              Return to sign in
            </Button>
          </Box>
        </Container>
      }
    </>
  );
}

export default ResetPassword;